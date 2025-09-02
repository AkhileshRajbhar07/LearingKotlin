package com.carbon.relay.integration.utils

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory

class JsonUtil private constructor() {
    companion object {
        private val logger = LoggerFactory.getLogger(JsonUtil::class.java)

        /**
         * Extracts "data" field from given JSON string and maps it to the target class.
         *
         * @param json The full JSON string containing "data"
         * @param clazz The target class type for the "data" object
         * @param objectMapper ObjectMapper bean for JSON handling
         * @return Deserialized object of type T or null if parsing fails
         */
        fun <T> fromJsonData(json: String, clazz: Class<T>, objectMapper: ObjectMapper): T? {
            return try {
                val rootNode: JsonNode = objectMapper.readTree(json)
                val dataNode: JsonNode? = rootNode.get("data")

                if (dataNode == null || dataNode.isNull) {
                    logger.warn("No 'data' field found in JSON: $json")
                    return null
                }

                objectMapper.treeToValue(dataNode, clazz)
            } catch (e: Exception) {
                logger.error("Failed to parse 'data' field into ${clazz.simpleName}. Error: ${e.message}", e)
                null
            }
        }
    }
}