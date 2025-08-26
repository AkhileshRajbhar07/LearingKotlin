package com.carbon.relay.integration.domains.customer.util

import com.carbon.relay.integration.domains.customer.models.Customer
import org.springframework.http.server.reactive.ServerHttpRequest
import java.util.*

object CustomerValidation {

    private fun getLocaleFromRequestOrConfig(request: ServerHttpRequest?): Locale {
        // 1. Try HTTP header
        val headerLang = request?.headers?.getFirst("Accept-Language")
            ?.split(",")?.firstOrNull()
            ?.let { Locale.forLanguageTag(it) }
        if (headerLang != null) return headerLang

        // 2. Default locale if no header
        return Locale.ENGLISH
    }

    private fun getBundle(locale: Locale): ResourceBundle =
        ResourceBundle.getBundle("customer-messages", locale)

    fun validateForCreate(customer: Customer, request: ServerHttpRequest? = null) {
        val locale = getLocaleFromRequestOrConfig(request)
        val bundle = getBundle(locale)
        require(!customer.fname.isNullOrBlank()) { bundle.getString("customer.fname.notblank") }
        require(!customer.lname.isNullOrBlank()) { bundle.getString("customer.lname.notblank") }
//        require(customer.dob != null && customer.dob.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) { bundle.getString("customer.dob.format") }
    }

    fun validateForUpdate(customer: Customer, request: ServerHttpRequest? = null) {
        val locale = getLocaleFromRequestOrConfig(request)
        val bundle = getBundle(locale)
        requireNotNull(customer.id) { bundle.getString("customer.id.notnull") }
        validateForCreate(customer, request)
    }
}