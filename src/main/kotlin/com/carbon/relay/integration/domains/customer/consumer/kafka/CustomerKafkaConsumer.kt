package com.carbon.relay.integration.domains.customer.consumer.kafka

import com.carbon.relay.integration.domains.customer.producer.CustomerRabbitProducer
import com.carbon.relay.integration.domains.customer.util.RedisRateLimiterUtil
import io.github.bucket4j.distributed.proxy.ProxyManager
import kotlinx.coroutines.runBlocking
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

/**
 * Kafka consumer for customer messages.
 * Listens to the 'customer' topic, stores messages in Redis, applies distributed rate limiting,
 * calls a third-party API, and publishes to RabbitMQ if allowed.
 */
@Component
class CustomerKafkaConsumer @Autowired constructor(
    private val reactiveRedisTemplate: ReactiveRedisTemplate<String, String>,
    private val webClient: WebClient,
    private val bucket4jProxyManager: ProxyManager<String>,
    private val customerRabbitProducer: CustomerRabbitProducer // Inject RabbitMQ producer
) {
    private val logger = LoggerFactory.getLogger(CustomerKafkaConsumer::class.java)

    /**
     * Handles incoming Kafka messages for the 'customer' topic.
     * Stores the message in Redis, applies distributed rate limiting,
     * calls a third-party API, and publishes to RabbitMQ if allowed.
     *
     * @param record The Kafka consumer record containing the message.
     */
    @KafkaListener(topics = ["customer"], groupId = "carbon-relay-integration")
    fun listen(record: ConsumerRecord<String, String>) = runBlocking {
        logger.info("Received customer message: ${record.value()}")
        // Step 1: Store message in Redis
        logger.debug("Storing message in Redis with key 'customer:last-message'")
        // Store message in Redis reactively
        reactiveRedisTemplate.opsForValue()
            .set("customer:last-message", record.value())
            .doOnSuccess { logger.info("Stored message in Redis with key 'customer:last-message'") }
            .doOnError { e -> logger.error("Failed to store message in Redis", e) }
            .subscribe()

        // Step 2: Distributed rate limiting with Bucket4j
        logger.debug("Checking distributed rate limit using Bucket4j")
        val maxRequests = 1L
        val windowSeconds = 60L
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
            // Step 3: Call third-party API
            logger.info("Rate limit check passed. Calling third-party API.")

            val response = webClient.put()
                .uri("https://postman-echo.com/put")
                .bodyValue(record.value())
                .retrieve()
                .toBodilessEntity()
                .block()

            logger.info("PUT to https://postman-echo.com/put responded with status: ${response?.statusCode}")
            // Step 4: Send message to RabbitMQ
            logger.debug("Sending message to RabbitMQ queue after successful third-party API call.")
            customerRabbitProducer.sendToQueue(record.value())
            logger.info("Message sent to RabbitMQ queue after third-party API call.")
        } else {
            logger.warn("Rate limit exceeded: more than $maxRequests requests in $windowSeconds seconds. Skipping external API call and RabbitMQ publish.")
        }
    }
}