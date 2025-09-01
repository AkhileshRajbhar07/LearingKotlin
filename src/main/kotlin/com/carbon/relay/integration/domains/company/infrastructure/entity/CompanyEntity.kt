package com.carbon.relay.integration.domains.company.infrastructure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("company")
data class CompanyEntity(
    @Id
    @Column("company_uuid")
    val companyUuid: String? = null,
    @Column("tenant_uuid")
    val tenantUuid: String,
    @Column("name")
    val name: String,
    @Column("street")
    val street: String?,
    @Column("zip")
    val zip: String?,
    @Column("city")
    val city: String?,
    @Column("created_at")
    val createdAt: LocalDateTime?,
    @Column("updated_at")
    val updatedAt: LocalDateTime?
)