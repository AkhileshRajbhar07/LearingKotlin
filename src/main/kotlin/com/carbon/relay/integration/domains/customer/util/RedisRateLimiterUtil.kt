package com.carbon.relay.integration.domains.customer.util

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.BucketConfiguration
import io.github.bucket4j.distributed.proxy.ProxyManager
import org.slf4j.Logger
import org.springframework.data.redis.core.ReactiveRedisTemplate
import reactor.core.publisher.Mono
import java.time.Duration

// Utility object for storing values in Redis and performing distributed rate limiting using Bucket4j and Redis
object RedisRateLimiterUtil {
    /**
     * Stores a value in Redis and applies distributed rate limiting using Bucket4j.
     *
     * @param redisTemplate ReactiveRedisTemplate for Redis operations
     * @param bucket4jProxyManager Bucket4j ProxyManager for distributed buckets (backed by Redis)
     * @param logger Logger for logging
     * @param storeKey Redis key to store the value
     * @param storeValue Value to store in Redis
     * @param rateLimitKey Redis key for the rate limit bucket
     * @param maxRequests Maximum allowed requests in the window
     * @param windowSeconds Time window in seconds for rate limiting
     * @return Mono<Boolean> indicating if the request is allowed (true) or rate-limited (false)
     */
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
            .then(
                Mono.fromCallable {
                    // Configure the rate limit: maxRequests per windowSeconds
                    val limit = Bandwidth.builder()
                        .capacity(maxRequests)
                        .refillGreedy(maxRequests, Duration.ofSeconds(windowSeconds))
                        .build()
                    val bucketConfig = BucketConfiguration.builder().addLimit(limit).build()
                    // Get or create a distributed bucket for the given key
                    val bucket = bucket4jProxyManager.builder().build(rateLimitKey) { bucketConfig }
                    // Try to consume one token from the bucket (returns true if allowed, false if rate-limited)
                    bucket.tryConsume(1)
                }
            )
            .onErrorResume { e ->
                logger.error("Unexpected error in storeAndRateLimitWithErrorHandling", e)
                Mono.just(false)
            }
    }
}