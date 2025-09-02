package com.carbon.relay.integration.domains.station.infrastructure.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


import java.time.LocalDateTime

@Entity
@Table(name = "charger_sync_success_event")
data class ChargerSyncSuccessEventEntity(
    @Id
    val chargerSyncSuccessEventId: String,
    val cpUuid: String,
    val type: String,
    val payload: String?, // JSONB
    val createdAt: LocalDateTime
)
