package com.carbon.relay.integration.domains.connectors.usecase

import com.carbon.relay.integration.domains.connectors.infrastructure.entity.ConnectorEntity
import com.carbon.relay.integration.domains.connectors.infrastructure.repositories.ConnectorRepository
import com.carbon.relay.integration.domains.station.usecase.StationService
import com.carbon.relay.integration.utils.mapper.ConnectorsObjectMapper
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ConnectorsService(
    private val connectorRepository: ConnectorRepository,
    private val objectMapper: ObjectMapper
) {

    private val logger = LoggerFactory.getLogger(StationService::class.java)

    suspend fun addConnectors(payload: String?) {
        val connectorsObjectMapper = ConnectorsObjectMapper(objectMapper)
        val connectorEntities = connectorsObjectMapper.kafkaJsonToConnectorEntity(payload)
        for (connectorEntity in connectorEntities) {
            logger.info(" $connectorEntity")
            connectorRepository.save<ConnectorEntity>(connectorEntity)
        }
    }
}