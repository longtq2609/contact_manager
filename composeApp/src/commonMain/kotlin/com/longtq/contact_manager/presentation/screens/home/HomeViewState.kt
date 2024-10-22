package com.longtq.contact_manager.presentation.screens.home

import com.longtq.contact_manager.domain.model.Contact

sealed interface HomeViewState {
    data object Loading : HomeViewState
    data class Success(val contact: List<Contact>) : HomeViewState
    data class Error(val error: String) : HomeViewState
}