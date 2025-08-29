package com.carbon.relay.integration.spring.rest.request

import kotlinx.serialization.Serializable
import java.time.LocalDate

/**
 * Value object for partial customer update (PATCH) requests.
 * Allows clients to update only specific fields of a customer.
 */
@Serializable
data class CustomerPatchRequest(
    val fname: String? = null, // New first name (optional)
    val lname: String? = null, // New last name (optional)
    val dob: LocalDate? = null // New date of birth (optional)
)
