package com.carbon.relay.integration.domains.customer.models

import java.time.LocalDate
import kotlinx.serialization.Serializable
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Serializable
@Table("customer")
data class Customer(
    @Id
    val id: Long? = null,
    val fname: String?,
    val lname: String?,
    val dob: LocalDate? // ISO format (yyyy-MM-dd)
)