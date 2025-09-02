package com.carbon.relay.integration.consumer

import com.carbon.relay.integration.domains.company.usecase.CompanyService
import com.carbon.relay.integration.domains.connectors.usecase.ConnectorsService
import com.carbon.relay.integration.domains.station.usecase.StationService
import com.carbon.relay.integration.domains.tenant.usecase.TenantService
import com.carbon.relay.integration.utils.ObjectSizeUtil
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service


@Service
class KafkaEventConsumer(
    private val tenantService: TenantService,
    private val companyService: CompanyService,
    private val stationService: StationService,
    private val connectorsService: ConnectorsService
) {

    private val logger = LoggerFactory.getLogger(KafkaEventConsumer::class.java)

    @KafkaListener(
        topics = ["\${kafka.topics.cp-manually-registered}"],
        groupId = "\${kafka.groups.cp-manually-registered}"

    )
    suspend fun consumeCpManuallyRegistered(message: String?) {
        logger.info("Received cp-manually-registered event: $message")
        if (message == null) {
            logger.error("Received null message for cp-manually-registered event")
            return
        }
//        stationService.createChargerStation(message)
    }

    @KafkaListener(
        topics = ["\${kafka.topics.cp-imported}"],
        groupId = "\${kafka.groups.cp-imported}"
    )
    fun consumeCpImported(message: String?) {
        logger.info("Received cp-imported event: $message")
    }

    @KafkaListener(
        topics = ["\${kafka.topics.cp-migrated}"],
        groupId = "\${kafka.groups.cp-migrated}"
    )
    fun consumeCpMigrated(message: String?) {
        logger.info("Received cp-migrated event: $message")
    }

    @KafkaListener(
        topics = ["\${kafka.topics.cp-automatically-registered}"],
        groupId = "\${kafka.groups.cp-automatically-registered}"
    )
    fun consumeCpAutomaticallyRegistered(message: String?) {
        logger.info("Received cp-automatically-registered event: $message")
    }

    @KafkaListener(
        topics = ["\${kafka.topics.cp-address-updated}"],
        groupId = "\${kafka.groups.cp-address-updated}"
    )
    fun consumeCpAddressUpdated(message: String?) {
        logger.info("Received cp-address-updated event: $message")
    }

    @KafkaListener(
        topics = ["\${kafka.topics.cp-company-updated}"],
        groupId = "\${kafka.groups.cp-company-updated}"
    )
    fun consumeCpCompanyUpdated(message: String?) {
        logger.info("Received cp-company-updated event: $message")
    }

    @KafkaListener(
        topics = ["\${kafka.topics.mandant-created}"],
        groupId = "\${kafka.groups.mandant-created}"
    )
    suspend fun consumeMandantCreated(records: List<ConsumerRecord<String, String>>, ack: Acknowledgment) {

        logger.debug(
            "Kafka payload received for mandant-created event: recordCount=${records.size}"
        )
        tenantService.createTenant(records, ack)
    }

    @KafkaListener(
        topics = ["\${kafka.topics.company-created}"],
        groupId = "\${kafka.groups.company-created}"
    )
    suspend fun consumeCompanyCreated(message: String?) {
        logger.info("Received company-created event: $message")

        if (message == null) {
            logger.error("Received null message for company-created event")
            return
        }
//        companyService.createCompany(message)
    }

    @KafkaListener(
        topics = ["\${kafka.topics.cp-connector-automatically-registered}"],
        groupId = "\${kafka.groups.cp-connector-automatically-registered}"
    )
    fun consumeCpConnectorAutomaticallyRegistered(message: String?) {
        logger.info("Received cp-connector-automatically-registered event: $message")
    }

    @KafkaListener(
        topics = ["\${kafka.topics.cp-connector-imported}"],
        groupId = "\${kafka.groups.cp-connector-imported}"
    )
    fun consumeCpConnectorImported(message: String?) {
        logger.info("Received cp-connector-imported event: $message")
    }

    @KafkaListener(
        topics = ["\${kafka.topics.cp-connector-manually-registered}"],
        groupId = "\${kafka.groups.cp-connector-manually-registered}"
    )
    fun consumeCpConnectorManuallyRegistered(message: String?) {
        logger.info("Received cp-connector-manually-registered event: $message")
    }

    @KafkaListener(
        topics = ["\${kafka.topics.cp-connector-migrated}"],
        groupId = "\${kafka.groups.cp-connector-migrated}"
    )
    fun consumeCpConnectorMigrated(message: String?) {
        logger.info("Received cp-connector-migrated event: $message")
    }

    @KafkaListener(
        topics = ["\${kafka.topics.evse-id-updated}"],
        groupId = "\${kafka.groups.evse-id-updated}"
    )
    fun consumeEvseIdUpdated(message: String?) {
        logger.info("Received evse-id-updated event: $message")
    }
}