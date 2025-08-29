package com.carbon.relay.integration.domains.customer.infrastructure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("tenant")
data class TenantEntity(
    @Id
    @org.springframework.data.relational.core.mapping.Column("mandant_uuid")
    val mandantUuid: String,
    @org.springframework.data.relational.core.mapping.Column("street")
    val street: String?,
    @org.springframework.data.relational.core.mapping.Column("zip")
    val zip: String?,
    @org.springframework.data.relational.core.mapping.Column("city")
    val city: String?,
    @org.springframework.data.relational.core.mapping.Column("created_at")
    val createdAt: LocalDateTime?,
    @org.springframework.data.relational.core.mapping.Column("updated_at")
    val updatedAt: LocalDateTime?
)
