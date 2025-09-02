package com.carbon.relay.integration.domains.tenant.infrastructure.entity


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "tenant")
@JsonIgnoreProperties
data class TenantEntity(
    @Id
    @Column("mandant_uuid")
    var mandantUuid: String? = null,

    @Column("name")
    val name: String? = null,

    @Column("street")
    val street: String? = null,

    @Column("zip")
    val zip: String? = null,

    @Column("city")
    val city: String? = null,

    @Column("created_at")
    val createdAt: LocalDateTime? = null,

    @Column("updated_at")
    val updatedAt: LocalDateTime? = null
)