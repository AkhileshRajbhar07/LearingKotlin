package com.carbon.relay.integration.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

/**
 * Configuration for Kafka producer.
 * Sets up the producer factory and KafkaTemplate beans for sending messages to Kafka.
 */
@Configuration
class KafkaProducerConfig {
    /**
     * Creates the Kafka producer factory with default configuration.
     * @return ProducerFactory for String keys and values
     */
//    @Bean
    fun producerFactory(): ProducerFactory<String, String> {
        val configProps = mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:29061",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
        )
        return DefaultKafkaProducerFactory(configProps)
    }

    /**
     * Creates the KafkaTemplate for sending messages to Kafka.
     * @return KafkaTemplate for String keys and values
     */
//    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, String> = KafkaTemplate(producerFactory())
}