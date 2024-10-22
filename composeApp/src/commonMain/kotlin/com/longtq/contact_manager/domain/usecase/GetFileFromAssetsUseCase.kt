package com.longtq.contact_manager.domain.usecase

import com.longtq.contact_manager.domain.repository.IContactRepository

class GetFileFromAssetsUseCase(
    private val contactRepository: IContactRepository
) {
    suspend operator fun invoke() = contactRepository.getFileFromAssets()
}