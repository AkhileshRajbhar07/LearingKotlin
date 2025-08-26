package com.carbon.relay.integration.domains.customer.consumer

import io.github.bucket4j.distributed.proxy.ProxyManager
import kotlinx.coroutines.runBlocking
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient


@Component
class CustomerKafkaConsumer @Autowired constructor(
    private val reactiveRedisTemplate: ReactiveRedisTemplate<String, String>,
    private val webClient: WebClient,
    private val bucket4jProxyManager: ProxyManager<String>
) {
    private val logger = LoggerFactory.getLogger(CustomerKafkaConsumer::class.java)

    @KafkaListener(topics = ["customer"], groupId = "qualicharge-integration")
    fun listen(record: ConsumerRecord<String, String>) = runBlocking {
        logger.info("Received customer message: ${record.value()}")
        val maxRequests = 1L
        val windowSeconds = 60L
        // Store message in Redis reactively
        reactiveRedisTemplate.opsForValue()
            .set("customer:last-message", record.value())
            .doOnSuccess { logger.info("Stored message in Redis with key 'customer:last-message'") }
            .doOnError { e -> logger.error("Failed to store message in Redis", e) }
            .subscribe()

        // Bucket4j distributed rate limiting
        val allowed = RedisRateLimiterUtil.storeAndRateLimitWithErrorHandling(
            reactiveRedisTemplate,
            bucket4jProxyManager,
            logger,
            "customer:last-message",
            record.value(),
            "customer:bucket4j-rate-limit",
            maxRequests,
            windowSeconds
        ).block() ?: false

        if (allowed) {
            val response = webClient.put()
                .uri("https://postman-echo.com/put")
                .bodyValue(record.value())
                .retrieve()
                .toBodilessEntity()
                .block()
            logger.info("PUT to https://postman-echo.com/put responded with status: ${'$'}{response?.statusCode}")
        } else {
            logger.warn("Rate limit exceeded: more than ${maxRequests}maxRequests requests in ${windowSeconds}windowSeconds seconds. Skipping external API call.")
        }
    }
}
