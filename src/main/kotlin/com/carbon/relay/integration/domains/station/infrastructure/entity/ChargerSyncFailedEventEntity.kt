package com.carbon.relay.integration.domains.station.infrastructure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("charger_sync_failed_event")
data class ChargerSyncFailedEventEntity(
    @Id
    @org.springframework.data.relational.core.mapping.Column("charger_sync_failed_event_id")
    val chargerSyncFailedEventId: String,
    @org.springframework.data.relational.core.mapping.Column("cp_uuid")
    val cpUuid: String,
    @org.springframework.data.relational.core.mapping.Column("type")
    val type: String,
    @org.springframework.data.relational.core.mapping.Column("payload")
    val payload: String?, // JSONB
    @org.springframework.data.relational.core.mapping.Column("reason")
    val reason: String?,
    @org.springframework.data.relational.core.mapping.Column("number_of_retries")
    val numberOfRetries: Int?,
    @org.springframework.data.relational.core.mapping.Column("created_at")
    val createdAt: LocalDateTime
)
