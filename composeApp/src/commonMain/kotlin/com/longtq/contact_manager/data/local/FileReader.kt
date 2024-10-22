package com.longtq.contact_manager.data.local

expect class FileReader() {
    suspend fun getJsonFilesFromAssets(): List<String>
    suspend fun readContactFromJson(fileName: String): String
}
