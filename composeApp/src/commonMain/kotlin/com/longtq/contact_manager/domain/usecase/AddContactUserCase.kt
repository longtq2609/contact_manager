package com.longtq.contact_manager.domain.usecase

import arrow.core.Either
import com.longtq.contact_manager.domain.model.Contact
import com.longtq.contact_manager.domain.repository.IContactRepository


class AddContactUserCase(
    private val contactRepository: IContactRepository
) {
    suspend operator fun invoke(contact: List<Contact>): Either<String, Unit> {
        return contactRepository.addContact(contact)
    }
}