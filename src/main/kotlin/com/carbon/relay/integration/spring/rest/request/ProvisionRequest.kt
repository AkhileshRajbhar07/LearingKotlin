package com.carbon.relay.integration.spring.rest.request

// Nested VOs for ProvisionRequest

data class ProvisionRequest(
    val tenant: Tenant,
    val company: Company,
    val station: Station,
    val connectors: List<Connector>
)

data class Tenant(
    val mandantUuid: String,
    val name: String,
    val street: String,
    val zip: String,
    val city: String
)

data class Company(
    val companyUuid: String,
    val name: String,
    val street: String,
    val zip: String,
    val city: String
)

data class Station(
    val cpUuid: String,
    val chargeboxIdentity: String,
    val pdcId: String,
    val label: String,
    val street: String,
    val zip: String,
    val city: String,
    val countryAlpha2: String
)

data class Connector(
    val uuid: String,
    val evseSearchString: String
)

