package com.carbon.relay.integration.domains.customer.infrastructure.entity

import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.annotation.Id

@Table("customer")
data class CustomerEntity(
    @Id
    val id: String? = null,
    val fName: String?,
    val lName: String?,
    val dob: String?
)


