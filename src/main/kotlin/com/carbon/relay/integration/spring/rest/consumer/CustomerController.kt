package com.carbon.relay.integration.spring.rest.consumer

import com.carbon.relay.integration.domains.customer.models.Customer
import com.carbon.relay.integration.domains.customer.models.CustomerResponse
import com.carbon.relay.integration.domains.customer.usecase.CreateCustomerUseCase
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController(val createCustomerUseCase: CreateCustomerUseCase) {

    val logger = KotlinLogging.logger {}

    @PostMapping("/customer")
    suspend fun createConsumer(@RequestBody customer: Customer): ResponseEntity<Any> {
        logger.info { "Received POST /customer request" }

        return try {
            logger.info { "Parsed request body: $customer" }
            val id = createCustomerUseCase.execute(customer)
            logger.info { "Customer created with id: $id" }

            ResponseEntity.ok(CustomerResponse(id.toString(), "created"))
        } catch (e: IllegalArgumentException) {
            logger.warn(e) { "Validation failed: ${e.message}" }
            ResponseEntity.badRequest().body(mapOf("error" to (e.message ?: "Invalid request")))
        } catch (e: Exception) {
            logger.error(e) { "Exception in /customer: ${e.message}" }
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("error" to "Internal server error"))
        }
    }
}