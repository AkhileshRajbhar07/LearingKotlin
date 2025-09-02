package com.carbon.relay.integration.domains.station.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


import java.time.LocalDateTime

@Entity
@Table(name = "session_sync_success_event")
data class SessionSyncSuccessEventEntity(
    @Id
    @Column("session_sync_success_event_id")
    val sessionSyncSuccessEventId: String,
    @Column("charge_log_uuid")
    val chargeLogUuid: String,
    @Column("payload")
    val payload: String,
    @Column("created_at")
    val createdAt: LocalDateTime
)
