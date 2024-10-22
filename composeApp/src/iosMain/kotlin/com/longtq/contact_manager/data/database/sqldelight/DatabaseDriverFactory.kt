package com.longtq.contact_manager.data.database.sqldelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class DatabaseDriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = ContactDatabase.Schema,
            name = "ContactDatabase.db"
        )
    }
}