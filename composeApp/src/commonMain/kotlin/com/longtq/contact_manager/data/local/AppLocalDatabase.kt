package com.longtq.contact_manager.data.local

import com.longtq.contact_manager.domain.model.Contact

interface AppLocalDatabase {
    suspend fun getFileFormAssets(): List<String>
    suspend fun readContactFromJson(fileName: String): List<Contact>
}