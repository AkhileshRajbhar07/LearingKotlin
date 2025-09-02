package com.carbon.relay.integration.domains.station.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "pending_charger_event_sync")
data class PendingChargerEventSyncEntity(
    @Id
    @Column("pending_charger_event_sync_id")
    val pendingChargerEventSyncId: String,
    @Column("cp_uuid")
    val cpUuid: String,
    @Column("type")
    val type: String,
    @Column("payload")
    val payload: String?, // TEXT
    @Column("created_at")
    val createdAt: java.time.LocalDateTime
)
