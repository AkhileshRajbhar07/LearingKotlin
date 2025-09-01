package com.carbon.relay.integration.producer

import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaEventProducer(private val kafkaTemplate: KafkaTemplate<String, String>) {

    private val logger = LoggerFactory.getLogger(KafkaEventProducer::class.java)

    /**
     * Sends a message to a specified Kafka topic.
     *
     * @param topic The Kafka topic name where the message should be sent.
     * @param key (Optional) The key associated with the message. If provided,
     *            Kafka will ensure all messages with the same key go to the same partition.
     *            If null, Kafka will use a round-robin partitioning strategy.
     * @param message The actual message payload to be sent (as a String).
     *
     * Example usage:
     * ```
     * sendMessage("order-events", "order-123", "{ \"status\": \"created\" }")
     * sendMessage("log-events", null, "Service started successfully")
     * ```
     */
    fun sendMessage(topic: String, key: String? = null, message: String) {
        if (key != null) {
            // Send message to Kafka with key (ensures ordering within the partition)
            kafkaTemplate.send(topic, key, message)
        } else {
            // Send message to Kafka without key (Kafka will distribute messages randomly or round-robin)
            kafkaTemplate.send(topic, message)
        }

        // Log the sent message (you may replace this with a proper logger like SLF4J)
        logger.info("✅ Sent message=[$message] to topic=[$topic] with key=[$key]")
    }
}