package com.carbon.relay.integration.domains.station.infrastructure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("charge_detail")
data class ChargeDetailEntity(
    @Id
    @org.springframework.data.relational.core.mapping.Column("charge_log_uuid")
    val chargeLogUuid: String,
    @org.springframework.data.relational.core.mapping.Column("connector_uuid")
    val connectorUuid: String,
    @org.springframework.data.relational.core.mapping.Column("meter_start")
    val meterStart: Float?,
    @org.springframework.data.relational.core.mapping.Column("meter_end")
    val meterEnd: Float?,
    @org.springframework.data.relational.core.mapping.Column("charge_log_end")
    val chargeLogEnd: java.time.LocalDateTime?,
    @org.springframework.data.relational.core.mapping.Column("charge_log_rec_end")
    val chargeLogRecEnd: java.time.LocalDateTime?,
    @org.springframework.data.relational.core.mapping.Column("created_at")
    val createdAt: java.time.LocalDateTime?
)
