package com.carbon.relay.integration.config.liquibase

import com.zaxxer.hikari.HikariDataSource
import liquibase.integration.spring.SpringLiquibase
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.slf4j.LoggerFactory
import javax.sql.DataSource

/**
 * Configuration for Liquibase database migrations.
 * Sets up the DataSource and SpringLiquibase beans using properties from spring.datasource.
 */
@Configuration
class LiquibaseConfig {
    private val logger = LoggerFactory.getLogger(LiquibaseConfig::class.java)

    /**
     * Creates a [DataSource] for Liquibase using HikariCP and spring.datasource properties.
     * @return DataSource instance
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun dataSource(): DataSource {
        logger.info("Creating DataSource for Liquibase")
        return DataSourceBuilder.create()
            .type(HikariDataSource::class.java)
            .build()
    }

    /**
     * Creates a [SpringLiquibase] bean for running database migrations.
     * @param dataSource DataSource instance
     * @return SpringLiquibase bean
     */
    @Bean
    fun liquibase(dataSource: DataSource): SpringLiquibase {
        logger.info("Creating SpringLiquibase bean")
        val liquibase = SpringLiquibase()
        liquibase.dataSource = dataSource
        liquibase.changeLog = "classpath:db/changelog/db.changelog-master.xml"
        return liquibase
    }
}