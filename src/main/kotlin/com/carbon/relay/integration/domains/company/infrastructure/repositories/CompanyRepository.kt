package com.carbon.relay.integration.domains.company.infrastructure.repositories

import com.carbon.relay.integration.domains.company.infrastructure.entity.CompanyEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CompanyRepository : JpaRepository<CompanyEntity, String>