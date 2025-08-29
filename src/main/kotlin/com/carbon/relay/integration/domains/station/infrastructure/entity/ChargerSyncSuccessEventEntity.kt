package com.carbon.relay.integration.domains.station.infrastructure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("charger_sync_success_event")
data class ChargerSyncSuccessEventEntity(
    @Id
    val chargerSyncSuccessEventId: String,
    val cpUuid: String,
    val type: String,
    val payload: String?, // JSONB
    val createdAt: LocalDateTime
)
