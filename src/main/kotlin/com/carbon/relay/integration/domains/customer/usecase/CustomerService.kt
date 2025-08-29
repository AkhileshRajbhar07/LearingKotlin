package com.carbon.relay.integration.domains.customer.usecase

import com.carbon.relay.integration.domains.customer.infrastructure.repositories.CustomerRepository
import com.carbon.relay.integration.domains.customer.infrastructure.entity.CustomerEntity
import com.carbon.relay.integration.spring.rest.request.CustomerPatchRequest
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

/**
 * Service class for customer business logic and data access.
 * Provides methods to get, update, and patch customers using the repository.
 */
@Service
class CustomerService(private val customerRepository: CustomerRepository) {
    /**
     * Retrieves all customers from the database.
     * @return List of all customers.
     */
    suspend fun getAllCustomers(): List<CustomerEntity> = customerRepository.findAll().collectList().awaitSingle()

    /**
     * Retrieves a customer by ID.
     * @param id The customer ID as a string.
     * @return The customer if found, null otherwise.
     */
    suspend fun getCustomerById(id: String): CustomerEntity? {
        val longId: Long = id.toLongOrNull() ?: return null
        return customerRepository.findById(longId).awaitSingleOrNull()
    }

    /**
     * Updates a customer completely (PUT).
     * @param id The customer ID as a string.
     * @param customer The updated customer entity.
     * @return The updated customer if found, null otherwise.
     */
    suspend fun updateCustomer(id: String, customer: CustomerEntity): CustomerEntity? {
        val longId: Long = id.toLongOrNull() ?: return null
        val existing: CustomerEntity = customerRepository.findById(longId).awaitSingleOrNull() ?: return null
        val updated: CustomerEntity = customer.copy(id = longId)
        return customerRepository.save(updated).awaitSingleOrNull()
    }

    /**
     * Partially updates a customer (PATCH).
     * @param id The customer ID as a string.
     * @param patch The patch request containing fields to update.
     * @return The patched customer if found, null otherwise.
     */
    suspend fun patchCustomer(id: String, patch: CustomerPatchRequest): CustomerEntity? {
        val longId: Long = id.toLongOrNull() ?: return null
        val existing: CustomerEntity = customerRepository.findById(longId).awaitSingleOrNull() ?: return null
        val updated: CustomerEntity = CustomerEntity(
            id = existing.id,
            fname = patch.fname ?: existing.fname,
            lname = patch.lname ?: existing.lname,
            dob = patch.dob ?: existing.dob
        )
        return customerRepository.save(updated).awaitSingleOrNull()
    }
}
