package com.carbon.relay.integration.utils.mapper

import com.carbon.relay.integration.domains.company.infrastructure.entity.CompanyEntity
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

class CompanyObjectMapper(val objectMapper: ObjectMapper) {

    fun kafkaJsonToCompanyEntity(json: String?): List<CompanyEntity> {
        if (json == null) {
            return emptyList()
        }
        val listOfCompanies = mutableListOf<CompanyEntity>()
        val listOfCompanyEntities = objectMapper.readValue(
            json,
            object : TypeReference<List<CompanyEntity>>() {})

        listOfCompanies.addAll(listOfCompanyEntities)
        return listOfCompanies
    }

}