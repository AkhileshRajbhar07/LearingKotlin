package com.carbon.relay.integration.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "redis")
class RedisProperties {
    lateinit var host: String
    var port: Int = 6379
    var authToken: String? = null
    var useTls: Boolean = false
    var iamTokenEndpoint: String? = null
}

