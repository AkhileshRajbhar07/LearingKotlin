package com.carbon.relay.integration.domains.connectors.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


import java.time.LocalDateTime

@Entity
@Table(name = "connectors")
data class ConnectorEntity(
    @Id
    @Column("connector_uuid")
    val connectorUuid: String,
    @Column("charger_station_uuid")
    val chargerStationUuid: String,
    @Column("evse_search_string")
    val evseSearchString: String?,
    @Column("created_at")
    val createdAt: LocalDateTime?,
    @Column("updated_at")
    val updatedAt: LocalDateTime?
)