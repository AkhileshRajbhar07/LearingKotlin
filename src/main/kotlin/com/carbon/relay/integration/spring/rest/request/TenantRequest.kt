package com.carbon.relay.integration.spring.rest.request

import java.time.LocalDateTime

data class TenantRequest(
    val street: String,
    val zip: String,
    val city: String,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)
