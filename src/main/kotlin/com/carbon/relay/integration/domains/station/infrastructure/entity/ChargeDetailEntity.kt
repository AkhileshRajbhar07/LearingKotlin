package com.carbon.relay.integration.domains.station.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "charge_detail")
data class ChargeDetailEntity(
    @Id
    @Column("charge_log_uuid")
    val chargeLogUuid: String,
    @Column("connector_uuid")
    val connectorUuid: String,
    @Column("meter_start")
    val meterStart: Float?,
    @Column("meter_end")
    val meterEnd: Float?,
    @Column("charge_log_end")
    val chargeLogEnd: java.time.LocalDateTime?,
    @Column("charge_log_rec_end")
    val chargeLogRecEnd: java.time.LocalDateTime?,
    @Column("created_at")
    val createdAt: java.time.LocalDateTime?
)
