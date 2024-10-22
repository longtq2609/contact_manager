package com.longtq.contact_manager.data.local

import com.longtq.contact_manager.data.local.model.ContactLocal
import com.longtq.contact_manager.data.mapper.toDomain
import com.longtq.contact_manager.domain.model.Contact
import kotlinx.serialization.json.Json

class LocalDatabaseImpl(private val fileReader: FileReader) : AppLocalDatabase {
    override suspend fun getFileFormAssets(): List<String> {
        return fileReader.getJsonFilesFromAssets()
    }

    override suspend fun readContactFromJson(fileName: String): List<Contact> {
        val jsonString = fileReader.readContactFromJson(fileName)
        val contactList = Json.decodeFromString<List<ContactLocal>>(jsonString)
        return contactList.map { it.toDomain() }
    }
}