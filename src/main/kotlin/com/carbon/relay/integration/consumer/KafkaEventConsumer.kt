package com.carbon.relay.integration.consumer

import com.carbon.relay.integration.domains.company.usecase.CompanyService
import com.carbon.relay.integration.domains.station.usecase.StationService
import com.carbon.relay.integration.domains.tenant.usecase.TenantService
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service


@Service
class KafkaEventConsumer(
    private val tenantService: TenantService,
    private val companyService: CompanyService,
    private val stationService: StationService
) {

    private val logger = LoggerFactory.getLogger(KafkaEventConsumer::class.java)

    @KafkaListener(
        topics = ["\${kafka.topics.domain_event.cp-manually-registered}"],
        groupId = "\${kafka.groups.domain_event.cp-manually-registered}"

    )
    suspend fun consumeCpManuallyRegistered(message: String?) {
        logger.info("Received cp-manually-registered event: " + message)
        if (message == null) {
            logger.error("Received null message for cp-manually-registered event")
            return
        }
        stationService.createChargerStation(message)
    }

    @KafkaListener(
        topics = ["\${kafka.topics.domain_event.cp-imported}"],
        groupId = "\${kafka.groups.domain_event.cp-imported}"
    )
    fun consumeCpImported(message: String?) {
        logger.info("Received cp-imported event: " + message)
    }

    @KafkaListener(
        topics = ["\${kafka.topics.domain_event.cp-migrated}"],
        groupId = "\${kafka.groups.domain_event.cp-migrated}"
    )
    fun consumeCpMigrated(message: String?) {
        logger.info("Received cp-migrated event: " + message)
    }

    @KafkaListener(
        topics = ["\${kafka.topics.domain_event.cp-automatically-registered}"],
        groupId = "\${kafka.groups.domain_event.cp-automatically-registered}"
    )
    fun consumeCpAutomaticallyRegistered(message: String?) {
        logger.info("Received cp-automatically-registered event: " + message)
    }

    @KafkaListener(
        topics = ["\${kafka.topics.domain_event.cp-address-updated}"],
        groupId = "\${kafka.groups.domain_event.cp-address-updated}"
    )
    fun consumeCpAddressUpdated(message: String?) {
        logger.info("Received cp-address-updated event: " + message)
    }

    @KafkaListener(
        topics = ["\${kafka.topics.domain_event.cp-company-updated}"],
        groupId = "\${kafka.groups.domain_event.cp-company-updated}"
    )
    fun consumeCpCompanyUpdated(message: String?) {
        logger.info("Received cp-company-updated event: " + message)
    }

    @KafkaListener(
        topics = ["\${kafka.topics.domain_event.mandant-created}"],
        groupId = "\${kafka.groups.domain_event.mandant-created}"
    )
    suspend fun consumeMandantCreated(message: String?) {
        logger.info("Received mandant-created event: " + message)
        if (message == null) {
            logger.error("Received null message for mandant-created event")
            return
        }
        tenantService.createTenant(message)
    }

    @KafkaListener(
        topics = ["\${kafka.topics.domain_event.company-created}"],
        groupId = "\${kafka.groups.domain_event.company-created}"
    )
    suspend fun consumeCompanyCreated(message: String?) {
        logger.info("Received company-created event: " + message)

        if (message == null) {
            logger.error("Received null message for company-created event")
            return
        }
        companyService.createCompany(message)
    }

    @KafkaListener(
        topics = ["\${kafka.topics.domain_event.cp-connector-automatically-registered}"],
        groupId = "\${kafka.groups.domain_event.cp-connector-automatically-registered}"
    )
    fun consumeCpConnectorAutomaticallyRegistered(message: String?) {
        logger.info("Received cp-connector-automatically-registered event: " + message)
    }

    @KafkaListener(
        topics = ["\${kafka.topics.domain_event.cp-connector-imported}"],
        groupId = "\${kafka.groups.domain_event.cp-connector-imported}"
    )
    fun consumeCpConnectorImported(message: String?) {
        logger.info("Received cp-connector-imported event: " + message)
    }

    @KafkaListener(
        topics = ["\${kafka.topics.domain_event.cp-connector-manually-registered}"],
        groupId = "\${kafka.groups.domain_event.cp-connector-manually-registered}"
    )
    fun consumeCpConnectorManuallyRegistered(message: String?) {
        logger.info("Received cp-connector-manually-registered event: " + message)
    }

    @KafkaListener(
        topics = ["\${kafka.topics.domain_event.cp-connector-migrated}"],
        groupId = "\${kafka.groups.domain_event.cp-connector-migrated}"
    )
    fun consumeCpConnectorMigrated(message: String?) {
        logger.info("Received cp-connector-migrated event: " + message)
    }

    @KafkaListener(
        topics = ["\${kafka.topics.domain_event.evse-id-updated}"],
        groupId = "\${kafka.groups.domain_event.evse-id-updated}"
    )
    fun consumeEvseIdUpdated(message: String?) {
        logger.info("Received evse-id-updated event: " + message)
    }
}