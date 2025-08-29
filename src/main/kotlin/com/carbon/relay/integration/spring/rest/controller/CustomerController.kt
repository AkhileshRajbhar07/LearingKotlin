package com.carbon.relay.integration.spring.rest.controller

import com.carbon.relay.integration.domains.customer.infrastructure.entity.CustomerEntity
import com.carbon.relay.integration.domains.customer.models.CustomerResponse
import com.carbon.relay.integration.domains.customer.usecase.CreateCustomerUseCase
import com.carbon.relay.integration.domains.customer.usecase.CustomerService
import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.carbon.relay.integration.spring.rest.request.CustomerPatchRequest
import com.carbon.relay.integration.spring.rest.request.CustomerRequest

/**
 * REST controller for managing Customer resources.
 * Handles creation, retrieval, update, and patch operations for customers.
 * Uses CustomerRequest and CustomerPatchRequest for input, and delegates business logic to service/use case layer.
 */
@Tag(name = "Customer", description = "Operations related to customers")
@RestController
class CustomerController(
    val createCustomerUseCase: CreateCustomerUseCase,
    val customerService: CustomerService
) {

    val logger = KotlinLogging.logger {}

    /**
     * Creates a new customer.
     * @param customerRequest The request payload containing customer details.
     * @return ResponseEntity with created customer ID and status.
     */
    @Operation(
        summary = "Create a new customer",
        description = "Creates a customer and returns the customer ID and status."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Customer created successfully"),
            ApiResponse(responseCode = "400", description = "Invalid request data"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ]
    )
    @PostMapping("/customer")
    suspend fun createConsumer(@RequestBody customerRequest: CustomerRequest): ResponseEntity<Any> {
        logger.info { "Received POST /customer request" }

        return try {
            logger.info { "Parsed request body: $customerRequest" }
            val customer = CustomerEntity(
                id = null,
                fname = customerRequest.fname,
                lname = customerRequest.lname,
                dob = customerRequest.dob
            )
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

    /**
     * Retrieves all customers.
     * @return ResponseEntity with a list of all customers.
     */
    @Operation(summary = "Get all customers", description = "Returns a list of all customers.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "List of customers returned successfully"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @GetMapping("/customer")
    suspend fun getAllCustomers(): ResponseEntity<Any> = try {
        val customers = customerService.getAllCustomers()
        ResponseEntity.ok(customers)
    } catch (e: Exception) {
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("error" to "Internal server error"))
    }

    /**
     * Retrieves a customer by ID.
     * @param id The customer ID.
     * @return ResponseEntity with the customer details or error if not found.
     */
    @Operation(summary = "Get customer by ID", description = "Returns a single customer by ID.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Customer returned successfully"),
        ApiResponse(responseCode = "404", description = "Customer not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @GetMapping("/customer/{id}")
    suspend fun getCustomerById(@PathVariable id: String): ResponseEntity<Any> = try {
        val customer = customerService.getCustomerById(id)
        if (customer != null) ResponseEntity.ok(customer)
        else ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "Customer not found"))
    } catch (e: Exception) {
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("error" to "Internal server error"))
    }

    /**
     * Updates a customer completely (PUT).
     * @param id The customer ID.
     * @param customerRequest The request payload containing updated customer details.
     * @return ResponseEntity with the updated customer or error if not found.
     */
    @Operation(summary = "Update customer", description = "Updates a customer completely (PUT).")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Customer updated successfully"),
        ApiResponse(responseCode = "404", description = "Customer not found"),
        ApiResponse(responseCode = "400", description = "Invalid request data"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @PutMapping("/customer/{id}")
    suspend fun updateCustomer(@PathVariable id: String, @RequestBody customerRequest: CustomerRequest): ResponseEntity<Any> = try {
        val customer = CustomerEntity(
            id = id.toLongOrNull(),
            fname = customerRequest.fname,
            lname = customerRequest.lname,
            dob = customerRequest.dob
        )
        val updated = customerService.updateCustomer(id, customer)
        if (updated != null) ResponseEntity.ok(updated)
        else ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "Customer not found"))
    } catch (e: IllegalArgumentException) {
        ResponseEntity.badRequest().body(mapOf("error" to (e.message ?: "Invalid request")))
    } catch (e: Exception) {
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("error" to "Internal server error"))
    }

    /**
     * Partially updates a customer (PATCH).
     * @param id The customer ID.
     * @param patch The request payload containing fields to update.
     * @return ResponseEntity with the patched customer or error if not found.
     */
    @Operation(summary = "Patch customer", description = "Updates a customer partially (PATCH).")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Customer patched successfully"),
        ApiResponse(responseCode = "404", description = "Customer not found"),
        ApiResponse(responseCode = "400", description = "Invalid request data"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @PatchMapping("/customer/{id}")
    suspend fun patchCustomer(@PathVariable id: String, @RequestBody patch: CustomerPatchRequest): ResponseEntity<Any> = try {
        val updated = customerService.patchCustomer(id, patch)
        if (updated != null) ResponseEntity.ok(updated)
        else ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "Customer not found"))
    } catch (e: IllegalArgumentException) {
        ResponseEntity.badRequest().body(mapOf("error" to (e.message ?: "Invalid request")))
    } catch (e: Exception) {
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("error" to "Internal server error"))
    }
}