package com.carbon.relay.integration.domains.station.usecase

import com.carbon.relay.integration.domains.station.infrastructure.entity.ChargerStationEntity
import com.carbon.relay.integration.domains.station.infrastructure.repositories.ChargerStationRepository
import com.carbon.relay.integration.spring.rest.request.ProvisionRequest
import com.carbon.relay.integration.spring.rest.request.StationRegisterRequest
import com.carbon.relay.integration.spring.rest.response.*
import com.carbon.relay.integration.utils.mapper.ChargerStationObjectMapper
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class StationService(
    private val chargerStationRepository: ChargerStationRepository,
    private val objectMapper: ObjectMapper
) {


    private val logger = LoggerFactory.getLogger(StationService::class.java)

    suspend fun createChargerStation(payload: String?) {
        val stationObjectMapper = ChargerStationObjectMapper(objectMapper)
        val stationEntities = stationObjectMapper.kafkaJsonToChargerStationEntity(payload)
        for (stationEntity in stationEntities) {
            logger.info(" $stationEntity")
            chargerStationRepository.save<ChargerStationEntity>(stationEntity).awaitSingle()
        }
    }

    fun registerStation(stationId: String, req: StationRegisterRequest): StationRegisterResponse {
        // Dummy implementation for now
        return StationRegisterResponse(
            stationId = stationId,
            pdcID = req.pdcID,
            status = "success",
            registeredConnectors = listOf(ConnectorStatus(connectorUuid = "dummy-uuid", status = "registered")),
            errors = null
        )
    }

    fun checkRegistration(stationId: String): StationRegistrationCheckResponse {
        // Dummy implementation for now
        return StationRegistrationCheckResponse(
            stationId = stationId,
            status = "ready",
            missingFields = null
        )
    }

    fun provision(req: ProvisionRequest): ProvisionResponse {
        // Dummy implementation for now
        return ProvisionResponse(
            status = "success",
            message = "Provisioning completed successfully"
        )
    }

    fun getStationData(stationId: String): StationDataResponse {
        // Dummy implementation for now
        return StationDataResponse(
            station = reqStation(),
            company = reqCompany(),
            tenant = reqTenant(),
            connectors = listOf(reqConnector())
        )
    }

    // Helper methods to create dummy data
    private fun reqStation() = com.carbon.relay.integration.spring.rest.request.Station(
        cpUuid = "dummy-cpUuid",
        chargeboxIdentity = "dummy-chargeboxIdentity",
        pdcId = "dummy-pdcId",
        label = "dummy-label",
        street = "dummy-street",
        zip = "dummy-zip",
        city = "dummy-city",
        countryAlpha2 = "DE"
    )

    private fun reqCompany() = com.carbon.relay.integration.spring.rest.request.Company(
        companyUuid = "dummy-companyUuid",
        name = "dummy-company",
        street = "dummy-street",
        zip = "dummy-zip",
        city = "dummy-city"
    )

    private fun reqTenant() = com.carbon.relay.integration.spring.rest.request.Tenant(
        mandantUuid = "dummy-mandantUuid",
        name = "dummy-tenant",
        street = "dummy-street",
        zip = "dummy-zip",
        city = "dummy-city"
    )

    private fun reqConnector() = com.carbon.relay.integration.spring.rest.request.Connector(
        uuid = "dummy-connectorUuid",
        evseSearchString = "dummy-evseSearchString"
    )
}

