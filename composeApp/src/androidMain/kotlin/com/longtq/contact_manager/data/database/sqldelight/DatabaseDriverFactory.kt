package com.longtq.contact_manager.data.database.sqldelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.longtq.contact_manager.MyApplication

actual class DatabaseDriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = ContactDatabase.Schema,
            context = MyApplication.instance,
            name = "ContactDatabase.db"
        )
    }
}