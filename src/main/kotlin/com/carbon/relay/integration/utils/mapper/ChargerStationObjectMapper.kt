package com.carbon.relay.integration.utils.mapper

import com.carbon.relay.integration.domains.station.infrastructure.entity.ChargerStationEntity
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

class ChargerStationObjectMapper(val objectMapper: ObjectMapper) {

    fun kafkaJsonToChargerStationEntity(json: String?): List<ChargerStationEntity> {
        if (json == null) {
            return emptyList()
        }
        val stationEntities = mutableListOf<ChargerStationEntity>()
        val chargerStationEntities = objectMapper.readValue(json, object : TypeReference<List<ChargerStationEntity>>() {})

        stationEntities.addAll(chargerStationEntities)
        return stationEntities
    }


}