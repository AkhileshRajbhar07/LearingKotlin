package com.carbon.relay.integration.domains.customer.infrastructure.repositories
import com.carbon.relay.integration.domains.customer.models.Customer
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : R2dbcRepository<Customer, String> {
}