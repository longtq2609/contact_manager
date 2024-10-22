package com.longtq.contact_manager.domain.usecase

import com.longtq.contact_manager.domain.repository.IContactRepository

class DeleteContactUseCase(
    private val contactRepository: IContactRepository
) {
    suspend operator fun invoke(idContact: List<Long>) {
        contactRepository.deleteContact(idContact)
    }
}