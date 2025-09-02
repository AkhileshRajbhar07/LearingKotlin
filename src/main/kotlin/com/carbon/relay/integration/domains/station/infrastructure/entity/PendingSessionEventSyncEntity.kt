package com.carbon.relay.integration.domains.station.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


import java.time.LocalDateTime

@Entity
@Table(name = "pending_session_event_sync")
data class PendingSessionEventSyncEntity(
    @Id
    @Column("pending_session_event_sync_id")
    val pendingSessionEventSyncId: String,
    @Column("charge_log_uuid")
    val chargeLogUuid: String?,
    @Column("payload")
    val payload: String?, // JSONB or CLOB, use String for now
    @Column("created_at")
    val createdAt: LocalDateTime?
)
