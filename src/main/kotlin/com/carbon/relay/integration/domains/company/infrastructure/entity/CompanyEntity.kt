package com.carbon.relay.integration.domains.company.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

import java.time.LocalDateTime

@Entity
@Table(name = "company")
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