package com.carbon.relay.integration.utils.mapper

import com.carbon.relay.integration.domains.station.infrastructure.entity.ChargerStationEntity
import com.carbon.relay.integration.domains.tenant.infrastructure.entity.TenantEntity
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

class ChargerStationObjectMapper(val objectMapper: ObjectMapper) {

    fun kafkaJsonToChargerStationEntity(json: String?): List<ChargerStationEntity> {
        if (json == null) {
            return emptyList()
        }
        val tenantEntities = mutableListOf<ChargerStationEntity>()
        val listOfTenant = objectMapper.readValue(json, object : TypeReference<List<ChargerStationEntity>>() {})

        tenantEntities.addAll(listOfTenant)
        return tenantEntities
    }

//    fun tenantRequestToKafkaJson(tenantRequest: TenantRequest): String {
//        val objectMapper = jacksonObjectMapper()
//        objectMapper
//            .registerModule(JavaTimeModule())
//            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//
//        val jsonPayload = objectMapper.writeValueAsString(tenantRequest)
//        return jsonPayload
//    }
}