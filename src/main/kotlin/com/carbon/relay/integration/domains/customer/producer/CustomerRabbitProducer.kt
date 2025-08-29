package com.carbon.relay.integration.domains.customer.producer

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.carbon.relay.integration.config.rabbit.RabbitMQConfig

@Component
class CustomerRabbitProducer @Autowired constructor(
    private val rabbitTemplate: RabbitTemplate
) {
    fun sendToQueue(message: String) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.CUSTOMER_QUEUE, message)
    }
}

