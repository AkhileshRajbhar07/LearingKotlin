package com.carbon.relay.integration

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

//@EnableR2dbcRepositories
@SpringBootApplication
@EnableJpaRepositories
class CarbonRelayIntegrationsApplication

fun main(args: Array<String>) {
	runApplication<CarbonRelayIntegrationsApplication>(*args)
}
