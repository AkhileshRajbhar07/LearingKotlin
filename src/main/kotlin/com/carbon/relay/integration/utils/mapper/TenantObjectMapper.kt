package com.carbon.relay.integration.utils.mapper

import com.carbon.relay.integration.domains.tenant.infrastructure.entity.TenantEntity
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

class TenantObjectMapper( val objectMapper: ObjectMapper) {

    fun kafkaJsonToTenantEntities(json: String?): List<TenantEntity> {
        if (json == null) {
            return emptyList()
        }
        val tenantEntities = mutableListOf<TenantEntity>()
        val listOfTenant = objectMapper.readValue(json, object : TypeReference<List<TenantEntity>>() {})

        tenantEntities.addAll(listOfTenant)
        return tenantEntities
    }

    fun kafkaJsonToTenantEntity(json: String?): TenantEntity {
        if (json == null) {
            throw IllegalArgumentException("JSON string cannot be null")
        }
        return objectMapper.readValue(json, object : TypeReference<TenantEntity>() {})
    }

    fun extractData(message: String): TenantEntity? {
        return try {
            val parsed: Map<String, Any> = objectMapper.readValue(message,)
            parsed["data"] as? TenantEntity
        } catch (e: Exception) {
            println("JSON parsing failed: ${e.message}")
            null
        }
    }


}