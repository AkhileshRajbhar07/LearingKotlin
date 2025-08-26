package com.carbon.relay.integration.domains.customer.infrastructure.repositories

//@Repository
//class CustomerMySQLRepository(private val dataSource: HikariDataSource) {
//    private val logger = KotlinLogging.logger {}
//    private val pgSQLRepository = MySQLRepository(dataSource)
//
//
//    fun createCustomer(customer: Customer): Long {
//        logger.info { "Inserting customer: $customer" }
//        val id = pgSQLRepository.insertAndReturnId(CustomerQueries.INSERT, listOf(customer.fname, customer.lname, customer.dob))
//        logger.info { "Inserted customer with id: $id" }
//        return id
//    }
//
//    fun updateCustomer(customer: Customer): Boolean {
//        logger.info { "Updating customer: $customer" }
//        val updated = pgSQLRepository.update(CustomerQueries.UPDATE, listOf(customer.fname, customer.lname, customer.dob, customer.id))
//        logger.info { "Updated customer with id: ${customer.id}, rows affected: $updated" }
//        return updated > 0
//    }
//
//    fun getCustomerById(id: Long): Customer? {
//        logger.info { "Fetching customer by id: $id" }
//        val customer = pgSQLRepository.querySingle(CustomerQueries.SELECT_BY_ID, listOf(id)) { rs ->
//            Customer(
//                id = rs.getString("id"),
//                fname = rs.getString("fname"),
//                lname = rs.getString("lname"),
//                dob = rs.getString("dob")
//            )
//        }
//        logger.info { "Fetched customer: $customer" }
//        return customer
//    }
//
//    fun getAllCustomers(): List<Customer> {
//        logger.info { "Fetching all customers" }
//        val customers = pgSQLRepository.queryList(CustomerQueries.SELECT_ALL) { rs ->
//            Customer(
//                id = rs.getString("id"),
//                fname = rs.getString("fname"),
//                lname = rs.getString("lname"),
//                dob = rs.getString("dob")
//            )
//        }
//        logger.info { "Fetched ${customers.size} customers" }
//        return customers
//    }
//}