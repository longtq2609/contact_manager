package com.longtq.contact_manager.domain.usecase

import com.longtq.contact_manager.domain.repository.IContactRepository

class SearchContactUseCase(
    private val contactRepository: IContactRepository
) {
    suspend operator fun invoke(query: String) = contactRepository.searchContact(query)
}