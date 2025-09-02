package com.carbon.relay.integration.domains.customer.infrastructure.repositories
import com.carbon.relay.integration.domains.customer.infrastructure.entity.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repository interface for Customer entity.
 * Extends R2dbcRepository to provide reactive CRUD operations.
 */
@Repository
interface CustomerRepository : JpaRepository<CustomerEntity, Long> {
}