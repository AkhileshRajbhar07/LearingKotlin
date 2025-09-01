package com.carbon.relay.integration.domains.tenant.infrastructure.entity


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("tenant")
@JsonIgnoreProperties(ignoreUnknown = true)
data class TenantEntity(
    @Id
    @Column("mandant_uuid")
    val mandantUuid: String? = null,
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