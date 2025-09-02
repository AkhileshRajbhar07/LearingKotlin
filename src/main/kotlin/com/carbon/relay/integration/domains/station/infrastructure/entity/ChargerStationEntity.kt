package com.carbon.relay.integration.domains.station.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


import java.time.LocalDateTime

@Entity
@Table(name = "charger_station")
data class ChargerStationEntity(
    @Id
    @Column("cp_uuid")
    val cpUuid: String? = null,
    @Column("mandant_uuid")
    val mandantUuid: String,
    @Column("company_uuid")
    val companyUuid: String,
    @Column("pdc")
    val pdc: String,
    @Column("chargebox_identity")
    val chargeboxIdentity: String?,
    @Column("label")
    val label: String?,
    @Column("street")
    val street: String?,
    @Column("zip")
    val zip: String?,
    @Column("city")
    val city: String?,
    @Column("country_alpha2")
    val countryAlpha2: String?,
    @Column("created_at")
    val createdAt: LocalDateTime?,
    @Column("updated_at")
    val updatedAt: LocalDateTime?
)
