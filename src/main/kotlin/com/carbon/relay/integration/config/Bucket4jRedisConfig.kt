package com.carbon.relay.integration.config

import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager
import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.codec.ByteArrayCodec
import io.lettuce.core.codec.RedisCodec
import io.lettuce.core.codec.StringCodec
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.nio.ByteBuffer

@Configuration
class Bucket4jRedisConfig {

    @Value("\${redis.host}")
    private lateinit var redisHost: String

    @Value("\${redis.port}")
    private lateinit var redisPort: String

    @Bean
    fun redisClient(): RedisClient {
        val redisUri = "redis://$redisHost:$redisPort"
        return RedisClient.create(redisUri)
    }

    @Bean
    fun connection(redisClient: RedisClient): StatefulRedisConnection<String, ByteArray> {
        return redisClient.connect(StringByteArrayCodec())
    }

    @Bean
    fun bucket4jProxyManager(
        connection: StatefulRedisConnection<String, ByteArray>
    ): LettuceBasedProxyManager<String> {
        return LettuceBasedProxyManager.builderFor(connection).build()
    }



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
