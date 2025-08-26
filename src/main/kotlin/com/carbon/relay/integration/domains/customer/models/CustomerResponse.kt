package com.carbon.relay.integration.domains.customer.models

import kotlinx.serialization.Serializable

@Serializable
data class CustomerResponse(val id: String, val status: String)