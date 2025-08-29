package com.carbon.relay.integration.domains.customer.usecase

import com.carbon.relay.integration.domains.customer.infrastructure.repositories.CustomerRepository
import com.carbon.relay.integration.domains.customer.infrastructure.entity.CustomerEntity
import com.carbon.relay.integration.domains.customer.util.CustomerValidation
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

/**
 * Use case/service for creating a new customer.
 * Validates input, persists the customer, and publishes creation event to Kafka.
 */
@Service
class CreateCustomerUseCase(
    private val customerRepository: CustomerRepository,
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    private val logger = KotlinLogging.logger {}

    /**
     * Executes the customer creation logic.
     * @param customer The customer entity to create.
     * @return The ID of the created customer, or null if creation failed.
     */
    suspend fun execute(customer: CustomerEntity): Long? {
        logger.info { "Validating customer for create: $customer" }
        CustomerValidation.validateForCreate(customer)
        val result = customerRepository.save<CustomerEntity>(customer).awaitSingle()

        // Publish to Kafka
        kafkaTemplate.send("customer", result.id.toString(),
            """{"id":${result.id},"fname":"${result.fname}","lname":"${result.lname}"}"""
        )

        logger.info { "Customer created with id: ${result.id}" }
        return result.id
    }

}