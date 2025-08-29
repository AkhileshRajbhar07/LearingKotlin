package com.carbon.relay.integration.spring.rest.response

data class StationRegisterResponse(
    val stationId: String?,
    val pdcID: String?,
    val status: String?,
    val registeredConnectors: List<ConnectorStatus>?,
    val errors: List<ConnectorError>? = null
)

// Helper VOs

data class ConnectorStatus(
    val connectorUuid: String,
    val status: String
)

data class ConnectorError(
    val connectorUuid: String,
    val error: String
)

