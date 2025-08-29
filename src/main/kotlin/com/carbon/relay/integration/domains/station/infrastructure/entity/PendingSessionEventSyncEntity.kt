package com.carbon.relay.integration.domains.station.infrastructure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("pending_session_event_sync")
data class PendingSessionEventSyncEntity(
    @Id
    @org.springframework.data.relational.core.mapping.Column("pending_session_event_sync_id")
    val pendingSessionEventSyncId: String,
    @org.springframework.data.relational.core.mapping.Column("charge_log_uuid")
    val chargeLogUuid: String?,
    @org.springframework.data.relational.core.mapping.Column("payload")
    val payload: String?, // JSONB or CLOB, use String for now
    @org.springframework.data.relational.core.mapping.Column("created_at")
    val createdAt: LocalDateTime?
)
