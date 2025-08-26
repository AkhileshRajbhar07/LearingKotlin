package com.carbon.relay.integration.domains.customer.consumer


import io.github.bucket4j.Bandwidth
import io.github.bucket4j.BucketConfiguration
import io.github.bucket4j.Refill
import io.github.bucket4j.distributed.proxy.ProxyManager
import org.slf4j.Logger
import org.springframework.data.redis.core.ReactiveRedisTemplate
import reactor.core.publisher.Mono
import java.time.Duration


object RedisRateLimiterUtil {
    fun storeAndRateLimitWithErrorHandling(
        redisTemplate: ReactiveRedisTemplate<String, String>,
        bucket4jProxyManager: ProxyManager<String>,
        logger: Logger,
        storeKey: String,
        storeValue: String,
        rateLimitKey: String,
        maxRequests: Long,
        windowSeconds: Long
    ): Mono<Boolean> {
        return redisTemplate.opsForValue()
            .set(storeKey, storeValue)
            .doOnSuccess { logger.info("Stored message in Redis with key '$storeKey'") }
            .doOnError { e -> logger.error("Failed to store message in Redis", e) }
            .then( // After storing, apply rate limiting
                Mono.fromCallable {
                    val limit = Bandwidth.classic(
                        maxRequests,
                        Refill.intervally(maxRequests, Duration.ofSeconds(windowSeconds))
                    )
                    val bucketConfig = BucketConfiguration.builder().addLimit(limit).build()

                    val bucket = bucket4jProxyManager.builder().build(rateLimitKey) { bucketConfig }

                    bucket.tryConsume(1) // returns Boolean
                }
            )
            .onErrorResume { e ->
                logger.error("Unexpected error in storeAndRateLimitWithErrorHandling", e)
                Mono.just(false)
            }
    }
}
