package com.carbon.relay.integration.domains.customer.consumer.rabbit

import com.carbon.relay.integration.config.rabbit.RabbitMQConfig
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class CustomerRabbitConsumer {
    private val logger = LoggerFactory.getLogger(CustomerRabbitConsumer::class.java)

    @RabbitListener(queues = [RabbitMQConfig.Companion.CUSTOMER_QUEUE])
    fun receiveMessage(message: String) {
        logger.info("Received message from RabbitMQ: $message")
        // Add your business logic here
    }
}