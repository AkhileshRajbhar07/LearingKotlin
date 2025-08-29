package com.carbon.relay.integration.domains.station.infrastructure.repositories

import com.carbon.relay.integration.domains.station.infrastructure.entity.ConnectorEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface ConnectorRepository : R2dbcRepository<ConnectorEntity, String>

