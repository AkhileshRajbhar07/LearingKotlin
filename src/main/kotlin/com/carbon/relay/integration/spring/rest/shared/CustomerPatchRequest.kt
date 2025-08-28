package com.carbon.relay.integration.spring.rest.shared

import java.time.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class CustomerPatchRequest(val fname: String? = null, val lname: String? = null, val dob: LocalDate? = null)
