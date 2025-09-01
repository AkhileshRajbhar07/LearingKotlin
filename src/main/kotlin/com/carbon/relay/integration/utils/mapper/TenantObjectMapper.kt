package com.carbon.relay.integration.utils.mapper

import com.carbon.relay.integration.domains.tenant.infrastructure.entity.TenantEntity
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

class TenantObjectMapper( val objectMapper: ObjectMapper) {

    fun kafkaJsonToTenantEntity(json: String?): List<TenantEntity> {
        if (json == null) {
            return emptyList()
        }
        val tenantEntities = mutableListOf<TenantEntity>()
        val listOfTenant = objectMapper.readValue(json, object : TypeReference<List<TenantEntity>>() {})

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