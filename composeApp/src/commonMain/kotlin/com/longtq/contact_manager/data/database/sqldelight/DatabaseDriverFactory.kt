package com.longtq.contact_manager.data.database.sqldelight

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory() {
    fun createDriver(): SqlDriver
}