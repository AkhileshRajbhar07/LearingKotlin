package com.carbon.relay.integration.domains.customer.infrastructure.entity

import kotlinx.serialization.Serializable
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

/**
 * Entity representing a customer in the database.
 * Used by the repository and service layers for persistence and business logic.
 */
@Serializable
@Table("customer")
data class CustomerEntity(
    @Id
    val id: Long? = null, // Database-generated customer ID
    val fname: String?,   // Customer's first name
    val lname: String?,   // Customer's last name
    val dob: LocalDate?   // Customer's date of birth (ISO format yyyy-MM-dd)
)