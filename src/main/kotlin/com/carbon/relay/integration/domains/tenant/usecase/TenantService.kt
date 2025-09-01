package com.carbon.relay.integration.domains.tenant.usecase

import com.carbon.relay.integration.domains.tenant.infrastructure.entity.TenantEntity
import com.carbon.relay.integration.domains.tenant.infrastructure.repositories.TenantRepository
import com.carbon.relay.integration.utils.mapper.TenantObjectMapper
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TenantService(
    private val tenantRepository: TenantRepository,
    private val objectMapper: ObjectMapper
) {

    private val logger = LoggerFactory.getLogger(TenantService::class.java)

    suspend fun createTenant(payload: String?) {
        val tenantObjectMapper = TenantObjectMapper(objectMapper)
        val tenantEntities = tenantObjectMapper.kafkaJsonToTenantEntity(payload)
        for (tenant in tenantEntities) {
            logger.info(" $tenant")
            tenantRepository.save<TenantEntity>(tenant).awaitSingle()
        }
    }
}