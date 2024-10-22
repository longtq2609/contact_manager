package com.longtq.contact_manager.data.repository

import arrow.core.Either
import com.longtq.contact_manager.data.database.AppDatabase
import com.longtq.contact_manager.data.local.AppLocalDatabase
import com.longtq.contact_manager.data.mapper.toDatabase
import com.longtq.contact_manager.data.mapper.toDomain
import com.longtq.contact_manager.domain.model.Contact
import com.longtq.contact_manager.domain.repository.IContactRepository

class ContactRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val appLocalDatabase: AppLocalDatabase
) : IContactRepository {

    override suspend fun addContact(contacts: List<Contact>): Either<String, Unit> {
        return appDatabase.addContact(contacts.map { it.toDatabase() })
    }

    override suspend fun getAllContacts(): List<Contact> {
        val listContact = appDatabase.getAllContacts()
        return listContact.map {
            it.toDomain()
        }
    }

    override suspend fun deleteContact(idContact: List<Long>) {
        appDatabase.deleteContact(idContact)
    }

    override suspend fun searchContact(query: String): List<Contact> {
        return appDatabase.searchContact(query).map { it.toDomain() }
    }

    override suspend fun getFileFromAssets(): List<String> {
        return appLocalDatabase.getFileFormAssets()
    }

    override suspend fun readContactFromJson(fileName: String): List<Contact> {
        return appLocalDatabase.readContactFromJson(fileName)
    }

}