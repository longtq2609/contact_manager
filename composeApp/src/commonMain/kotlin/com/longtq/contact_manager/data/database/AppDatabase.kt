package com.longtq.contact_manager.data.database

import arrow.core.Either
import com.longtq.contact_manager.data.database.model.ContactDatabase


interface AppDatabase {
    suspend fun addContact(contacts: List<ContactDatabase>): Either<String, Unit>
    suspend fun getAllContacts(): List<ContactDatabase>
    suspend fun deleteContact(contact: List<Long>)
    suspend fun searchContact(query: String): List<ContactDatabase>
}