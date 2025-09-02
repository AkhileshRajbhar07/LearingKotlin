package com.carbon.relay.integration.domains.station.infrastructure.repositories

import com.carbon.relay.integration.domains.station.infrastructure.entity.ChargeDetailEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChargeDetailRepository : JpaRepository<ChargeDetailEntity, String>

