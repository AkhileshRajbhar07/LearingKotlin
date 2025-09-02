package com.carbon.relay.integration.domains.station.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "session_sync_failed_event")
data class SessionSyncFailedEventEntity(
    @Id
    @Column("session_sync_failed_event_id")
    val sessionSyncFailedEventId: String,
    @Column("charge_log_uuid")
    val chargeLogUuid: String,
    @Column("payload")
    val payload: String?, // JSONB
    @Column("reason")
    val reason: String?,
    @Column("number_of_retries")
    val numberOfRetries: Int?,
    @Column("created_at")
    val createdAt: java.time.LocalDateTime
)
