package com.carbon.relay.integration.config.redis

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.slf4j.LoggerFactory

/**
 * Configuration for Redis connection and reactive Redis template.
 * Uses properties from [RedisProperties] for connection details and TLS support.
 */
@Configuration
class RedisConfig(
    private val redisProperties: RedisProperties
) {
    private val logger = LoggerFactory.getLogger(RedisConfig::class.java)

    /**
     * Creates a [LettuceConnectionFactory] for Redis using the configured properties.
     * @return LettuceConnectionFactory instance
     */
    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        logger.info("Creating LettuceConnectionFactory with host: ${redisProperties.host}, port: ${redisProperties.port}, useTls: ${redisProperties.useTls}")
        val config = RedisStandaloneConfiguration(redisProperties.host, redisProperties.port)
        redisProperties.authToken?.let { config.setPassword(it) }
        val clientConfig = if (redisProperties.useTls) {
            LettuceClientConfiguration.builder().useSsl().build()
        } else {
            LettuceClientConfiguration.builder().build()
        }
        return LettuceConnectionFactory(config, clientConfig)
    }

    /**
     * Creates a [ReactiveRedisTemplate] for reactive Redis operations.
     * @param factory LettuceConnectionFactory instance
     * @return ReactiveRedisTemplate for String keys and values
     */
    @Bean
    fun reactiveRedisTemplate(factory: LettuceConnectionFactory): ReactiveRedisTemplate<String, String> {
        logger.info("Creating ReactiveRedisTemplate")
        return ReactiveRedisTemplate(factory, RedisSerializationContext.string())
    }
}