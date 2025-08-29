package com.carbon.relay.integration.config.bucket4j

import com.carbon.relay.integration.config.redis.RedisProperties
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager
import io.lettuce.core.RedisClient
import io.lettuce.core.codec.ByteArrayCodec
import io.lettuce.core.codec.RedisCodec
import io.lettuce.core.codec.StringCodec
import io.lettuce.core.api.StatefulRedisConnection
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.nio.ByteBuffer

/**
 * Configuration for Bucket4j rate limiting using Redis as the backend store.
 * Sets up Redis client, connection, and Bucket4j proxy manager beans.
 * Uses properties from [RedisProperties] for connection details and TLS support.
 */
@Configuration
class Bucket4jRedisConfig(
    private val redisProperties: com.carbon.relay.integration.config.redis.RedisProperties
) {
    private val logger = LoggerFactory.getLogger(Bucket4jRedisConfig::class.java)

    /**
     * Creates a [RedisClient] for Bucket4j using the configured Redis properties.
     * @return RedisClient instance
     */
    @Bean
    fun redisClient(): RedisClient {
        val scheme = if (redisProperties.useTls) "rediss" else "redis"
        val passwordPart = redisProperties.authToken?.let { ":$it@" } ?: ""
        val redisUri = "$scheme://$passwordPart${redisProperties.host}:${redisProperties.port}"
        logger.info("Creating RedisClient for Bucket4j with URI: $redisUri")
        return RedisClient.create(redisUri)
    }

    /**
     * Creates a [StatefulRedisConnection] for Bucket4j using the provided Redis client.
     * @param redisClient RedisClient instance
     * @return StatefulRedisConnection for String keys and ByteArray values
     */
    @Bean
    fun connection(redisClient: RedisClient): StatefulRedisConnection<String, ByteArray> {
        logger.info("Creating StatefulRedisConnection for Bucket4j")
        return redisClient.connect(StringByteArrayCodec())
    }

    /**
     * Creates a [LettuceBasedProxyManager] for Bucket4j using the provided Redis connection.
     * @param connection StatefulRedisConnection instance
     * @return LettuceBasedProxyManager for distributed rate limiting
     */
    @Bean
    fun bucket4jProxyManager(
        connection: StatefulRedisConnection<String, ByteArray>
    ): LettuceBasedProxyManager<String> {
        logger.info("Creating LettuceBasedProxyManager for Bucket4j")
        return LettuceBasedProxyManager.builderFor(connection).build()
    }

    /**
     * Codec for encoding String keys and ByteArray values for Redis operations.
     */
    class StringByteArrayCodec : RedisCodec<String, ByteArray> {
        private val stringCodec = StringCodec.UTF8
        private val byteArrayCodec = ByteArrayCodec.INSTANCE

        override fun decodeKey(bytes: ByteBuffer?): String {
            return stringCodec.decodeKey(bytes)
        }

        override fun encodeKey(key: String?): ByteBuffer {
            return stringCodec.encodeKey(key)
        }

        override fun decodeValue(bytes: ByteBuffer?): ByteArray {
            return byteArrayCodec.decodeValue(bytes)
        }

        override fun encodeValue(value: ByteArray?): ByteBuffer {
            return byteArrayCodec.encodeValue(value)
        }
    }

}