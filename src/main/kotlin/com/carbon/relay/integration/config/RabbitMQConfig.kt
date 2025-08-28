package com.carbon.relay.integration.config

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {
    companion object {
        const val CUSTOMER_QUEUE = "customer.rabbit.queue"
    }

    // Inject RabbitMQ properties from application.yaml
    @Value("\${rabbitmq.host}")
    private lateinit var rabbitHost: String

    @Value("\${rabbitmq.port}")
    private var rabbitPort: Int = 5676

    @Value("\${rabbitmq.username}")
    private lateinit var rabbitUsername: String

    @Value("\${rabbitmq.password}")
    private lateinit var rabbitPassword: String

    @Value("\${rabbitmq.virtual-host}")
    private lateinit var rabbitVirtualHost: String

    @Bean
    fun queue(): Queue = Queue(CUSTOMER_QUEUE, true)

    @Bean
    fun rabbitConnectionFactory(): ConnectionFactory {
        val factory = CachingConnectionFactory()
        factory.setHost(rabbitHost)
        factory.port = rabbitPort
        factory.username = rabbitUsername
        factory.setPassword(rabbitPassword)
        factory.virtualHost = rabbitVirtualHost
        return factory
    }

    @Bean
    fun rabbitTemplate(rabbitConnectionFactory: ConnectionFactory): RabbitTemplate = RabbitTemplate(rabbitConnectionFactory)
}
