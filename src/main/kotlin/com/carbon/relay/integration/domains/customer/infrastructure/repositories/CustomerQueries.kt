package com.carbon.relay.integration.domains.customer.infrastructure.repositories

object CustomerQueries {
    const val INSERT = "INSERT INTO customer (fname, lname, dob) VALUES (?, ?, ?)"
    const val UPDATE = "UPDATE customer SET fname = ?, lname = ?, dob = ? WHERE id = ?"
    const val SELECT_BY_ID = "SELECT id, fname, lname, dob FROM customer WHERE id = ?"
    const val SELECT_ALL = "SELECT id, fname, lname, dob FROM customer"
}

