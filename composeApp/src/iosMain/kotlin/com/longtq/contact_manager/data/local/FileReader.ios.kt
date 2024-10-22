package com.longtq.contact_manager.data.local

actual class FileReader actual constructor() {
    actual suspend fun getJsonFilesFromAssets(): List<String> {
        TODO("Not yet implemented")
    }

    actual suspend fun readContactFromJson(fileName: String): String {
        TODO("Not yet implemented")
    }
}