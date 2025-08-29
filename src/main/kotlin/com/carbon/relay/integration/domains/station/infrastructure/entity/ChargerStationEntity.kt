package com.carbon.relay.integration.domains.station.infrastructure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("charger_station")
data class ChargerStationEntity(
    @Id
    @org.springframework.data.relational.core.mapping.Column("cp_uuid")
    val cpUuid: String,
    @org.springframework.data.relational.core.mapping.Column("mandant_uuid")
    val mandantUuid: String,
    @org.springframework.data.relational.core.mapping.Column("company_uuid")
    val companyUuid: String,
    @org.springframework.data.relational.core.mapping.Column("pdc")
    val pdc: String,
    @org.springframework.data.relational.core.mapping.Column("chargebox_identity")
    val chargeboxIdentity: String?,
    @org.springframework.data.relational.core.mapping.Column("label")
    val label: String?,
    @org.springframework.data.relational.core.mapping.Column("street")
    val street: String?,
    @org.springframework.data.relational.core.mapping.Column("zip")
    val zip: String?,
    @org.springframework.data.relational.core.mapping.Column("city")
    val city: String?,
    @org.springframework.data.relational.core.mapping.Column("country_alpha2")
    val countryAlpha2: String?,
    @org.springframework.data.relational.core.mapping.Column("created_at")
    val createdAt: LocalDateTime?,
    @org.springframework.data.relational.core.mapping.Column("updated_at")
    val updatedAt: LocalDateTime?
)
