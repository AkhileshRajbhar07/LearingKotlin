package com.carbon.relay.integration.config.rabbit

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
class RabbitMQProperties {
    lateinit var host: String
    var port: Int = 5676
    lateinit var username: String
    lateinit var password: String
    lateinit var virtualHost: String
}

