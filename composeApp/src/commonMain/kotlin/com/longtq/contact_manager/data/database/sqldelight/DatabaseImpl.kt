package com.longtq.contact_manager.data.database.sqldelight

import arrow.core.Either
import com.longtq.contact_manager.data.database.AppDatabase
import com.longtq.contact_manager.data.database.model.ContactDatabase
import kotlin.random.Random

class DatabaseImpl(private val appDatabase: Database) : AppDatabase {

    override suspend fun addContact(contacts: List<ContactDatabase>): Either<String, Unit> {
        var result: Either<String, Unit> = Either.Right(Unit)
        appDatabase {
            for (contact in contacts) {
                val isNameEmpty = contact.name.isEmpty() || contact.name.isBlank()
                val isEmailEmpty = contact.email.isEmpty() || contact.email.isBlank()
                val isPhoneEmpty = contact.phone.isEmpty() || contact.phone.isBlank()

                if (isNameEmpty || isEmailEmpty || isPhoneEmpty) {
                    result = Either.Left("Please fill in all information.")
                    break
                }

                val existingPhoneContacts =
                    it.contactDatabaseQueries.getContactByPhone(contact.phone).executeAsList()
                val existingEmailContacts =
                    it.contactDatabaseQueries.getContactByEmail(contact.email).executeAsList()
                if (existingPhoneContacts.isNotEmpty() && existingEmailContacts.isNotEmpty()) {
                    result = Either.Left("Cannot add duplicate email and phone number information ")
                    break
                }

                it.contactDatabaseQueries.insertContact(
                    contact.name, contact.email, contact.phone, randomNumber().toLong()
                )
            }
        }
        return result
    }


    override suspend fun getAllContacts(): List<ContactDatabase> {
        return appDatabase { it ->
            it.contactDatabaseQueries.getAllContacts().executeAsList().map {
                ContactDatabase(
                    id = it.id,
                    name = it.name,
                    email = it.email,
                    phone = it.phone,
                    typeImage = it.typeImage ?: 0
                )
            }
        }
    }

    override suspend fun deleteContact(contact: List<Long>) {
        appDatabase {
            it.contactDatabaseQueries.deleteContacts(contact)
        }
    }

    override suspend fun searchContact(query: String): List<ContactDatabase> {
        return appDatabase { it ->
            it.contactDatabaseQueries.searchContacts(query).executeAsList().map {
                ContactDatabase(
                    id = it.id,
                    name = it.name,
                    email = it.email,
                    phone = it.phone,
                    typeImage = it.typeImage ?: 0
                )
            }
        }
    }

    private fun randomNumber(): Int {
        return Random.nextInt(1, 11)
    }

}

