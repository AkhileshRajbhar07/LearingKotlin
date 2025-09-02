package com.carbon.relay.integration.utils.mapper

import com.carbon.relay.integration.domains.connectors.infrastructure.entity.ConnectorEntity
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

class ConnectorsObjectMapper(val objectMapper: ObjectMapper) {

    fun kafkaJsonToConnectorEntity(json: String?): List<ConnectorEntity> {
        if (json == null) {
            return emptyList()
        }
        val connectorEntities = mutableListOf<ConnectorEntity>()
        val listOfConnectors = objectMapper.readValue(json, object : TypeReference<List<ConnectorEntity>>() {})

        connectorEntities.addAll(listOfConnectors)
        return connectorEntities
    }

}