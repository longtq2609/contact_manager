package com.longtq.contact_manager.presentation.screens.add_contact

sealed interface AddContactViewState {
    data object Loading : AddContactViewState
    data object Success : AddContactViewState
    data class Error(val error: String) : AddContactViewState
}