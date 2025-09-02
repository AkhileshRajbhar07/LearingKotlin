package com.carbon.relay.integration.domains.connectors.infrastructure.repositories

import com.carbon.relay.integration.domains.connectors.infrastructure.entity.ConnectorEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConnectorRepository : JpaRepository<ConnectorEntity, String>