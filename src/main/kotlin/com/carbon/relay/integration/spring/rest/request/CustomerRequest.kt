package com.carbon.relay.integration.spring.rest.request

import java.time.LocalDate

/**
 * Value object for customer creation and update requests.
 * Used by the controller to receive customer data from clients.
 */
data class CustomerRequest(
    val fname: String?, // Customer's first name
    val lname: String?, // Customer's last name
    val dob: LocalDate? // Customer's date of birth
)
