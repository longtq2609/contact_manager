package com.longtq.contact_manager.domain.usecase

import com.longtq.contact_manager.domain.repository.IContactRepository


class GetAllContactsUseCase(
    private val contactRepository: IContactRepository
) {
    suspend operator fun invoke() =
        contactRepository.getAllContacts()

}