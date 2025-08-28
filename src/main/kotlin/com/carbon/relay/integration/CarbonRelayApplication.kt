package com.carbon.relay.integration

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@EnableR2dbcRepositories
@SpringBootApplication
class CarbonRelayIntegrationsApplication

fun main(args: Array<String>) {
	runApplication<CarbonRelayIntegrationsApplication>(*args)
}
