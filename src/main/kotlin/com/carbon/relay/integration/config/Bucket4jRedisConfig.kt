package com.carbon.relay.integration.config

import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager
import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.codec.ByteArrayCodec
import io.lettuce.core.codec.StringCodec
import io.lettuce.core.codec.CompositeCodec
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory

@Configuration
class Bucket4jRedisConfig {

    @Value("\${spring.redis.host}")
    private lateinit var redisHost: String

    @Value("\${spring.redis.port}")
    private lateinit var redisPort: String

    @Bean
    fun redisClient(): RedisClient {
        val redisUri = "redis://$redisHost:$redisPort"
        return RedisClient.create(redisUri)
    }

    @Bean
    fun lettuceConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory(redisHost, redisPort.toInt())
    }

//    @Bean
//    fun bucket4jProxyManager(redisClient: RedisClient): LettuceBasedProxyManager<String> {
//        val connection = redisClient.connect(StringCodec.UTF8)
//        return LettuceBasedProxyManager
//            .builderFor(connection)
//            .build() as LettuceBasedProxyManager<String>
//    }

    @Bean
    fun redisConnection(redisClient: RedisClient): StatefulRedisConnection<String?, String?>? {
        // Keys = String, Values = ByteArray → required by Bucket4j
        val codec = CompositeCodec(StringCodec.UTF8, ByteArrayCodec.INSTANCE)
        return redisClient.connect(codec)
    }

    @Bean
    fun bucket4jProxyManager(
        redisConnection: StatefulRedisConnection<String, ByteArray>
    ): LettuceBasedProxyManager<String> {
        // Safe cast: builder always returns LettuceBasedProxyManager
        return LettuceBasedProxyManager
            .builderFor(redisConnection)
            .build() as LettuceBasedProxyManager<String>
    }

}
