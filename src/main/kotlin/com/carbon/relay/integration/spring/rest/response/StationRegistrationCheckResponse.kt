package com.carbon.relay.integration.spring.rest.response

data class StationRegistrationCheckResponse(
    val stationId: String,
    val status: String,
    val missingFields: List<String>? = null
)

