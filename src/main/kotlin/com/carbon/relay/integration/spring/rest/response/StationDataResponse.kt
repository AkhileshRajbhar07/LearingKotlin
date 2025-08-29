package com.carbon.relay.integration.spring.rest.response

import com.carbon.relay.integration.spring.rest.request.Station
import com.carbon.relay.integration.spring.rest.request.Company
import com.carbon.relay.integration.spring.rest.request.Tenant
import com.carbon.relay.integration.spring.rest.request.Connector

data class StationDataResponse(
    val station: Station,
    val company: Company,
    val tenant: Tenant,
    val connectors: List<Connector>
)

