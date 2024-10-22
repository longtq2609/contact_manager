package com.longtq.contact_manager.domain.repository

import arrow.core.Either
import com.longtq.contact_manager.domain.model.Contact


interface IContactRepository {
    suspend fun addContact(contacts: List<Contact>): Either<String, Unit>
    suspend fun getAllContacts(): List<Contact>
    suspend fun deleteContact(idContact: List<Long>)
    suspend fun searchContact(query: String): List<Contact>
    suspend fun getFileFromAssets(): List<String>
    suspend fun readContactFromJson(fileName: String): List<Contact>
}