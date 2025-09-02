package com.carbon.relay.integration.utils

import java.util.UUID

object UuidUtil {
    /**
     * Generate a random UUID.
     * @return A new random UUID.
     */
    fun generate(): String {
        return UUID.randomUUID().toString()
    }
}