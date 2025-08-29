package com.carbon.relay.integration.domains.station.infrastructure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("session_sync_success_event")
data class SessionSyncSuccessEventEntity(
    @Id
    @org.springframework.data.relational.core.mapping.Column("session_sync_success_event_id")
    val sessionSyncSuccessEventId: String,
    @org.springframework.data.relational.core.mapping.Column("charge_log_uuid")
    val chargeLogUuid: String,
    @org.springframework.data.relational.core.mapping.Column("payload")
    val payload: String,
    @org.springframework.data.relational.core.mapping.Column("created_at")
    val createdAt: LocalDateTime
)
