package com.carbon.relay.integration.config

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ObjectMapperConfig {

    @Bean
    fun jacksonMapper(): com.fasterxml.jackson.databind.ObjectMapper {
        val objectMapper = com.fasterxml.jackson.module.kotlin.jacksonObjectMapper()

        return objectMapper
            .registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    }
}