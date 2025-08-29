package com.carbon.relay.integration.domains.customer.infrastructure.repositories

import com.carbon.relay.integration.domains.customer.infrastructure.entity.TenantEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface TenantRepository : R2dbcRepository<TenantEntity, String>

