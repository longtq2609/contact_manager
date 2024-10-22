package com.longtq.contact_manager.presentation.screens.add_contact

import cafe.adriel.voyager.core.model.ScreenModel
import com.longtq.contact_manager.domain.model.Contact
import com.longtq.contact_manager.domain.usecase.AddContactUserCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AddContactViewModel(
    private val addContactUserCase: AddContactUserCase,
) : ScreenModel {
    private val job = SupervisorJob()
    private val coroutineContext: CoroutineContext = job + Dispatchers.IO
    private val viewModelScope = CoroutineScope(coroutineContext)
    private val _viewState = MutableStateFlow<AddContactViewState>(AddContactViewState.Loading)
    val viewState = _viewState.asStateFlow()

    fun addContact(name: String, phone: String, email: String) {
        viewModelScope.launch {
            try {
                val contacts = Contact(name = name, phone = phone, email = email)
                val listContacts = listOf(
                    contacts
                )
                _viewState.value = AddContactViewState.Loading
                val result = addContactUserCase.invoke(listContacts)
                result.fold(
                    { errorMessage ->
                        _viewState.value = AddContactViewState.Error(errorMessage)

                    },
                    {
                        _viewState.value = AddContactViewState.Success
                    }
                )

            } catch (e: Exception) {
                e.printStackTrace()
                _viewState.value = AddContactViewState.Error(e.message.toString())
            }
        }
    }
}