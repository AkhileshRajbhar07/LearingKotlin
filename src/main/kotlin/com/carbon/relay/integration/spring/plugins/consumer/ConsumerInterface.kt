package com.carbon.relay.integration.spring.plugins.consumer

interface ConsumerInterface {
    suspend fun startConsuming()
}