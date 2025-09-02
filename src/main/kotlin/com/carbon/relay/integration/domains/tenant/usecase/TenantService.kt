package com.carbon.relay.integration.domains.tenant.usecase

import com.carbon.relay.integration.domains.tenant.infrastructure.entity.TenantEntity
import com.carbon.relay.integration.domains.tenant.infrastructure.repositories.TenantRepository
import com.carbon.relay.integration.utils.JsonUtil
import com.carbon.relay.integration.utils.UuidUtil
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
class TenantService(
    private val tenantRepository: TenantRepository,
    private val objectMapper: ObjectMapper
) {

    @Value("\${kafka.topics.mandant-created}")
    private val LOG_PREFIX: String? = null


    private val logger = LoggerFactory.getLogger(TenantService::class.java)

    suspend fun createTenant(records: List<ConsumerRecord<String, String>>, ack: Acknowledgment) {
        val totalRecords = records.size
        logger.info("$LOG_PREFIX Received $totalRecords record(s) for tenant processing")

        val validTenants = mutableListOf<TenantEntity>()
        var failedCount = 0

        records.forEachIndexed { index, record ->
            val payload = record.value()
            val key = record.key()

            logger.debug("$LOG_PREFIX Processing record #$index with key=$key")

            try {
                val tenantEntity = JsonUtil.fromJsonData(payload, TenantEntity::class.java, objectMapper)
                if (tenantEntity != null) {
                    if(tenantEntity.mandantUuid==null || tenantEntity.mandantUuid!!.isEmpty()){
                        tenantEntity.mandantUuid= UuidUtil.generate()
                    }
                    validTenants.add(tenantEntity)
                    logger.debug("$LOG_PREFIX Successfully parsed tenant entity for key=$key")
                } else {
                    logger.warn("$LOG_PREFIX Null tenant entity parsed for key=$key")
                    failedCount++
                }
            } catch (e: Exception) {
                logger.error(
                    "$LOG_PREFIX Failed to parse record with key=$key. Payload=$payload. Error=${e.message}",
                    e
                )
                failedCount++
            }
        }


        if (validTenants.isNotEmpty()) {
            tenantRepository.saveAll(validTenants)
            logger.info("$LOG_PREFIX Saved ${validTenants.size} tenant record(s) to database")
        } else {
            logger.warn("$LOG_PREFIX No valid tenant records found to persist")
        }

        logger.info("$LOG_PREFIX Processing summary: total=$totalRecords, success=${validTenants.size}, failed=$failedCount")
        ack.acknowledge()
    }
}