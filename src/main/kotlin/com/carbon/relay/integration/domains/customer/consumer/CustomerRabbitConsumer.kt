package com.carbon.relay.integration.domains.customer.consumer

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.slf4j.LoggerFactory
import com.carbon.relay.integration.config.RabbitMQConfig

@Component
class CustomerRabbitConsumer {
    private val logger = LoggerFactory.getLogger(CustomerRabbitConsumer::class.java)

    @RabbitListener(queues = [RabbitMQConfig.CUSTOMER_QUEUE])
    fun receiveMessage(message: String) {
        logger.info("Received message from RabbitMQ: $message")
        // Add your business logic here
    }
}

