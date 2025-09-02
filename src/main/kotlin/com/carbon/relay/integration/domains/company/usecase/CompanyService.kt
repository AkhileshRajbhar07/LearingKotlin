package com.carbon.relay.integration.domains.company.usecase

import com.carbon.relay.integration.domains.company.infrastructure.entity.CompanyEntity
import com.carbon.relay.integration.domains.company.infrastructure.repositories.CompanyRepository
import com.carbon.relay.integration.utils.mapper.CompanyObjectMapper
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CompanyService(
    private val companyRepository: CompanyRepository,
    private val objectMapper: ObjectMapper
) {
    private val logger = LoggerFactory.getLogger(CompanyService::class.java)

    suspend fun createCompany(payload: String?) {
        // Implementation for creating a company goes here
        logger.info("Creating company with payload: $payload")
        // You can use companyRepository and objectMapper as needed
        val companyObjectMapper = CompanyObjectMapper(objectMapper)
        val companyEntities = companyObjectMapper.kafkaJsonToCompanyEntity(payload)
        for (companyEntity in companyEntities) {
            logger.info(" $companyEntity")
            companyRepository.save<CompanyEntity>(companyEntity)
        }
    }
}