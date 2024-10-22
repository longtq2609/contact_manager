package com.longtq.contact_manager.domain.usecase

import com.longtq.contact_manager.domain.repository.IContactRepository

class ReadContactFromJsonUseCase(
    private val contactRepository: IContactRepository
) {
    suspend operator fun invoke(jsonString: String) =
        contactRepository.readContactFromJson(jsonString)
}