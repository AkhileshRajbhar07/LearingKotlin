package com.carbon.relay.integration.domains.station.infrastructure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("session_sync_failed_event")
data class SessionSyncFailedEventEntity(
    @Id
    @org.springframework.data.relational.core.mapping.Column("session_sync_failed_event_id")
    val sessionSyncFailedEventId: String,
    @org.springframework.data.relational.core.mapping.Column("charge_log_uuid")
    val chargeLogUuid: String,
    @org.springframework.data.relational.core.mapping.Column("payload")
    val payload: String?, // JSONB
    @org.springframework.data.relational.core.mapping.Column("reason")
    val reason: String?,
    @org.springframework.data.relational.core.mapping.Column("number_of_retries")
    val numberOfRetries: Int?,
    @org.springframework.data.relational.core.mapping.Column("created_at")
    val createdAt: java.time.LocalDateTime
)
