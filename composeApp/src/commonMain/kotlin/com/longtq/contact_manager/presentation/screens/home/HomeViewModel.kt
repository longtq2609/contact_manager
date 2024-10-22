package com.longtq.contact_manager.presentation.screens.home

import cafe.adriel.voyager.core.model.ScreenModel
import com.longtq.contact_manager.domain.model.Contact
import com.longtq.contact_manager.domain.usecase.AddContactUserCase
import com.longtq.contact_manager.domain.usecase.DeleteContactUseCase
import com.longtq.contact_manager.domain.usecase.GetAllContactsUseCase
import com.longtq.contact_manager.domain.usecase.GetFileFromAssetsUseCase
import com.longtq.contact_manager.domain.usecase.ReadContactFromJsonUseCase
import com.longtq.contact_manager.domain.usecase.SearchContactUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class HomeViewModel(
    private val getAllContactsUseCase: GetAllContactsUseCase,
    private val addContactUserCase: AddContactUserCase,
    private val searchContactUseCase: SearchContactUseCase,
    private val getFileFromAssetsUseCase: GetFileFromAssetsUseCase,
    private val readContactFromJsonUseCase: ReadContactFromJsonUseCase,
    private val deleteContactUseCase: DeleteContactUseCase
) : ScreenModel {
    private val job = SupervisorJob()
    private val coroutineContext: CoroutineContext = job + Dispatchers.IO
    private val viewModelScope = CoroutineScope(coroutineContext)

    private val _viewState = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val viewState = _viewState.asStateFlow()

    private val _file = MutableStateFlow<List<String>>(emptyList())
    val file = _file.asStateFlow()

    private val _listIdChecked = MutableStateFlow<List<Long>>(emptyList())
    val listIdChecked = _listIdChecked.asStateFlow()

    private var currentContacts: List<Contact> = emptyList()

    init {
        getAllContacts()
        getFileFromAssets()
    }

    fun getAllContacts() {
        viewModelScope.launch {
            _viewState.value = HomeViewState.Loading
            try {
                currentContacts = withContext(Dispatchers.IO) {
                    getAllContactsUseCase.invoke()
                }
                _viewState.value = HomeViewState.Success(currentContacts)
            } catch (e: Exception) {
                e.printStackTrace()
                _viewState.value = HomeViewState.Error(e.message.toString())
            }
        }
    }

    private fun addContact(contacts: List<Contact>) {
        viewModelScope.launch {
            try {
                _viewState.value = HomeViewState.Loading
                val result = addContactUserCase.invoke(contacts)
                result.fold(
                    { errorMessage ->
                        _viewState.value = HomeViewState.Error(errorMessage)

                    },
                    {
                        _viewState.value = HomeViewState.Success(contacts)
                        getAllContacts()
                    }
                )
            } catch (e: Exception) {
                e.printStackTrace()
                _viewState.value = HomeViewState.Error(e.message.toString())
            }
        }
    }

    fun searchContact(text: String) {
        viewModelScope.launch {
            try {
                val contacts = searchContactUseCase.invoke(text)
                if (contacts.isEmpty())
                    _viewState.value = HomeViewState.Error("No results found")
                else
                    _viewState.value = HomeViewState.Success(contacts)
            } catch (e: Exception) {
                e.printStackTrace()
                _viewState.value = HomeViewState.Error(e.message.toString())
            }
        }
    }

    private fun getFileFromAssets() {
        viewModelScope.launch {
            val file = getFileFromAssetsUseCase.invoke()
            _file.value = file
        }
    }

    fun readJsonFromFile(fileName: String) {
        viewModelScope.launch {
            _viewState.value = HomeViewState.Loading
            try {
                val listContact = withContext(Dispatchers.IO) {
                    readContactFromJsonUseCase.invoke(fileName)
                }
                addContact(listContact)
                _viewState.value = HomeViewState.Success(currentContacts)
            } catch (e: Exception) {
                e.printStackTrace()
                _viewState.value = HomeViewState.Error(e.message.toString())
            }
        }
    }


    fun updateContactCheckStatus(updatedContact: Contact) {
        viewModelScope.launch {
            val currentState = _viewState.value
            if (currentState is HomeViewState.Success) {
                val updatedContacts = currentState.contact.map {
                    if (it.id == updatedContact.id) {
                        updatedContact
                    } else {
                        it
                    }
                }
                _listIdChecked.value =
                    updatedContacts.filter { it.isChecked ?: false }.map { it.id.toLong() }
                _viewState.value = HomeViewState.Success(updatedContacts)
            }
        }
    }

    fun selectAllContacts(isSelected: Boolean) {
        viewModelScope.launch {
            try {
                val currentState = _viewState.value
                if (currentState is HomeViewState.Success) {
                    val updatedList = currentState.contact.map { contact ->
                        contact.copy(isChecked = isSelected)
                    }
                    _listIdChecked.value =
                        updatedList.filter { it.isChecked ?: false }.map { it.id }
                    _viewState.value = HomeViewState.Success(updatedList)
                }
            } catch (e: Exception) {
                _viewState.value = HomeViewState.Error("Error selecting all contacts")
            }
        }
    }


    fun deleteSelectedContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteContactUseCase.invoke(_listIdChecked.value)
                currentContacts = currentContacts.filterNot { contact ->
                    _listIdChecked.value.contains(contact.id)
                }
                _viewState.value = HomeViewState.Success(currentContacts)
            } catch (e: Exception) {
                _viewState.value = HomeViewState.Error("Error deleting selected contacts")
            }
        }
    }


}