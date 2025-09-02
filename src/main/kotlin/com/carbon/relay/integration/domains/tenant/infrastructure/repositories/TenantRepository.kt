package com.carbon.relay.integration.domains.tenant.infrastructure.repositories

import com.carbon.relay.integration.domains.tenant.infrastructure.entity.TenantEntity
import org.springframework.data.jpa.repository.JpaRepository


interface TenantRepository : JpaRepository<TenantEntity, String>