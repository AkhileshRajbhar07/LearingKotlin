package com.carbon.relay.integration.domains.customer.models

import kotlinx.serialization.Serializable

/**
 * Value object for API responses after customer creation.
 * Contains the customer ID and status message.
 */
@Serializable
data class CustomerResponse(
    val id: String,    // Created customer ID
    val status: String // Status message (e.g., "created")
)
