package com.carbon.relay.integration.utils.mapper

import com.carbon.relay.integration.domains.company.infrastructure.entity.CompanyEntity
import com.carbon.relay.integration.domains.tenant.infrastructure.entity.TenantEntity
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

class CompanyObjectMapper(val objectMapper: ObjectMapper) {

    fun kafkaJsonToCompanyEntity(json: String?): List<CompanyEntity> {
        if (json == null) {
            return emptyList()
        }
        val tenantEntities = mutableListOf<CompanyEntity>()
        val listOfTenant = objectMapper.readValue(
            json,
            object : TypeReference<List<CompanyEntity>>() {})

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