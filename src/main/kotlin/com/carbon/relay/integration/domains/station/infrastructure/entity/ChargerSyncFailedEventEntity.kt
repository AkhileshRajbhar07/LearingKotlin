package com.carbon.relay.integration.domains.station.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

import java.time.LocalDateTime

@Entity
@Table(name = "charger_sync_failed_event")
data class ChargerSyncFailedEventEntity(
    @Id
    @Column("charger_sync_failed_event_id")
    val chargerSyncFailedEventId: String,
    @Column("cp_uuid")
    val cpUuid: String,
    @Column("type")
    val type: String,
    @Column("payload")
    val payload: String?, // JSONB
    @Column("reason")
    val reason: String?,
    @Column("number_of_retries")
    val numberOfRetries: Int?,
    @Column("created_at")
    val createdAt: LocalDateTime
)
