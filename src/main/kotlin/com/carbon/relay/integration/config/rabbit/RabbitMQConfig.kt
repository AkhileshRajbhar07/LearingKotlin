package com.carbon.relay.integration.config.rabbit

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.slf4j.LoggerFactory

/**
 * Configuration for RabbitMQ messaging.
 * Sets up the queue, connection factory, and RabbitTemplate beans using properties from RabbitMQProperties.
 */
@Configuration
class RabbitMQConfig(
    private val rabbitMQProperties: RabbitMQProperties
) {
    private val logger = LoggerFactory.getLogger(RabbitMQConfig::class.java)

    companion object {
        const val CUSTOMER_QUEUE = "customer.rabbit.queue"
    }

    /**
     * Creates the RabbitMQ queue for customer messages.
     * @return Queue instance
     */
    @Bean
    fun queue(): Queue {
        logger.info("Creating RabbitMQ queue: $CUSTOMER_QUEUE")
        return Queue(CUSTOMER_QUEUE, true)
    }

    /**
     * Creates the RabbitMQ connection factory using configured properties.
     * @return ConnectionFactory instance
     */
    @Bean
    fun rabbitConnectionFactory(): ConnectionFactory {
        logger.info("Creating RabbitMQ ConnectionFactory with host: ${rabbitMQProperties.host}, port: ${rabbitMQProperties.port}, vhost: ${rabbitMQProperties.virtualHost}")
        val factory = CachingConnectionFactory()
        factory.setHost(rabbitMQProperties.host)
        factory.port = rabbitMQProperties.port
        factory.username = rabbitMQProperties.username
        factory.setPassword(rabbitMQProperties.password)
        factory.virtualHost = rabbitMQProperties.virtualHost
        return factory
    }

    /**
     * Creates the RabbitTemplate for sending messages to RabbitMQ.
     * @param rabbitConnectionFactory ConnectionFactory instance
     * @return RabbitTemplate instance
     */
    @Bean
    fun rabbitTemplate(rabbitConnectionFactory: ConnectionFactory): RabbitTemplate {
        logger.info("Creating RabbitTemplate")
        return RabbitTemplate(rabbitConnectionFactory)
    }
}