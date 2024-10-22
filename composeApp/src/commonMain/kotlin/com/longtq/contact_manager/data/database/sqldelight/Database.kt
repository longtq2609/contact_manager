package com.longtq.contact_manager.data.database.sqldelight

class Database(private val databaseDriverFactory: DatabaseDriverFactory) {
    private var database: ContactDatabase? = null

    private fun initDatabase() {
        if (database == null) {
            database = ContactDatabase.invoke(databaseDriverFactory.createDriver())
        }
    }

    suspend operator fun <R> invoke(block: suspend (ContactDatabase) -> R): R {
        initDatabase()
        return block(database!!)
    }
}