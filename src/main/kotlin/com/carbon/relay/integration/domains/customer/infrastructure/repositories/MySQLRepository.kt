package com.carbon.relay.integration.domains.customer.infrastructure.repositories

import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class MySQLRepository(private val dataSource: HikariDataSource) {
    fun getConnection(): Connection = dataSource.connection

    fun testQuery(): List<Map<String, Any?>> {
        getConnection().use { conn ->
            conn.createStatement().use { stmt ->
                stmt.executeQuery("SELECT 1 as test").use { rs ->
                    return resultSetToList(rs)
                }
            }
        }
    }

    private fun resultSetToList(rs: ResultSet): List<Map<String, Any?>> {
        val md = rs.metaData
        val columns = (1..md.columnCount).map { md.getColumnName(it) }
        val result = mutableListOf<Map<String, Any?>>()
        while (rs.next()) {
            val row = columns.associateWith { rs.getObject(it) }
            result.add(row)
        }
        return result
    }

    fun <T> querySingle(sql: String, params: List<Any?> = emptyList(), mapper: (ResultSet) -> T?): T? {
        getConnection().use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                params.forEachIndexed { idx, value -> stmt.setObject(idx + 1, value) }
                stmt.executeQuery().use { rs ->
                    return if (rs.next()) mapper(rs) else null
                }
            }
        }
    }

    fun <T> queryList(sql: String, params: List<Any?> = emptyList(), mapper: (ResultSet) -> T): List<T> {
        getConnection().use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                params.forEachIndexed { idx, value -> stmt.setObject(idx + 1, value) }
                stmt.executeQuery().use { rs ->
                    val result = mutableListOf<T>()
                    while (rs.next()) {
                        result.add(mapper(rs))
                    }
                    return result
                }
            }
        }
    }

    fun update(sql: String, params: List<Any?> = emptyList()): Int {
        getConnection().use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                params.forEachIndexed { idx, value -> stmt.setObject(idx + 1, value) }
                return stmt.executeUpdate()
            }
        }
    }

    fun insertAndReturnId(sql: String, params: List<Any?> = emptyList()): Long {
        getConnection().use { conn ->
            conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stmt ->
                params.forEachIndexed { idx, value -> stmt.setObject(idx + 1, value) }
                stmt.executeUpdate()
                stmt.generatedKeys.use { rs ->
                    if (rs.next()) return rs.getLong(1)
                }
            }
        }
        throw Exception("No generated key returned")
    }
}
