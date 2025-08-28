package com.carbon.relay.integration.domains.customer.usecase

//import com.qualicharge.integration.domains.customer.infrastructure.repositories.CustomerMySQLRepository
import com.carbon.relay.integration.domains.customer.infrastructure.repositories.CustomerRepository
import com.carbon.relay.integration.domains.customer.models.Customer
import com.carbon.relay.integration.domains.customer.util.CustomerValidation
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class CreateCustomerUseCase(
    private val customerRepository: CustomerRepository,
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    private val logger = KotlinLogging.logger {}
    suspend fun execute(customer: Customer): Long? {
        logger.info { "Validating customer for create: $customer" }
        CustomerValidation.validateForCreate(customer)
//        val id = customerRepo.createCustomer(customer)
//        val result = customerRepository.save<Customer>(customer).subscribe { obj ->
//            logger.info { "Customer created with id: ${obj.id}" }
//            obj.id
//        }
        val result = customerRepository.save<Customer>(customer).awaitSingle()

        // Publish to Kafka
        kafkaTemplate.send("customer", result.id.toString(),
            """{"id":${result.id},"fname":"${result.fname}","lname":"${result.lname}"}""")

        logger.info { "Customer created with id: ${result.id}" }
        return result.id
    }

}