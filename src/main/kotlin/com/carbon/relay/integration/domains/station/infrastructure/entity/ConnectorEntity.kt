package com.carbon.relay.integration.domains.station.infrastructure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("connectors")
data class ConnectorEntity(
    @Id
    @org.springframework.data.relational.core.mapping.Column("connector_uuid")
    val connectorUuid: String,
    @org.springframework.data.relational.core.mapping.Column("charger_station_uuid")
    val chargerStationUuid: String,
    @org.springframework.data.relational.core.mapping.Column("evse_search_string")
    val evseSearchString: String?,
    @org.springframework.data.relational.core.mapping.Column("created_at")
    val createdAt: LocalDateTime?,
    @org.springframework.data.relational.core.mapping.Column("updated_at")
    val updatedAt: LocalDateTime?
)
