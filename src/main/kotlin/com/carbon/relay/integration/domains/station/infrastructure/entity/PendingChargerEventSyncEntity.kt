package com.carbon.relay.integration.domains.station.infrastructure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("pending_charger_event_sync")
data class PendingChargerEventSyncEntity(
    @Id
    @org.springframework.data.relational.core.mapping.Column("pending_charger_event_sync_id")
    val pendingChargerEventSyncId: String,
    @org.springframework.data.relational.core.mapping.Column("cp_uuid")
    val cpUuid: String,
    @org.springframework.data.relational.core.mapping.Column("type")
    val type: String,
    @org.springframework.data.relational.core.mapping.Column("payload")
    val payload: String?, // TEXT
    @org.springframework.data.relational.core.mapping.Column("created_at")
    val createdAt: java.time.LocalDateTime
)
