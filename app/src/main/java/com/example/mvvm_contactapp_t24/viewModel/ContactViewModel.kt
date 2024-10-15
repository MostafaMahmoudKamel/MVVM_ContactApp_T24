package com.example.mvvm_contactapp_t24.viewModel

import androidx.lifecycle.ViewModel
import com.example.mvvm_contactapp_t24.model.Contact
import com.example.mvvm_contactapp_t24.repository.ContactRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ContactViewModel : ViewModel() {

    private val contactRepository = ContactRepositoryImpl()

    //listOfContact
    private var _contactList = MutableStateFlow<List<Contact>>(emptyList())
    var contactList: StateFlow<List<Contact>> = _contactList

    //index
    private var _index = MutableStateFlow<Int>(-1)
    var index: StateFlow<Int> = _index

    //name txt
    private var _name = MutableStateFlow("")
    var name: StateFlow<String> = _name

    //phone txt
    private var _phone = MutableStateFlow("")
    var phone: StateFlow<String> = _phone

    //address txt
    private var _address = MutableStateFlow("")
    var address: StateFlow<String> = _address

    //email txt
    private var _email = MutableStateFlow("")
    var email: StateFlow<String> = _email

    //search txt
    private var _search = MutableStateFlow("")
    var search: StateFlow<String> = _search

    //nameError txt
    private var _nameError = MutableStateFlow(false)
    var nameError: StateFlow<Boolean> = _nameError

    //phoneError txt
    private var _phoneError = MutableStateFlow(false)
    var phoneError: StateFlow<Boolean> = _phoneError

    //addressError txt
    private var _addressError = MutableStateFlow(false)
    var addressError: StateFlow<Boolean> = _addressError

    private var _emailError = MutableStateFlow(false)
    var emailError: StateFlow<Boolean> = _emailError

    //modalBottom Add
    private var _isAddModalBottomVisible = MutableStateFlow(false)
    var isAddModalBottomVisible: StateFlow<Boolean> = _isAddModalBottomVisible

    //modal Bottom Update
    private var _isUpdateModalBottomVisible = MutableStateFlow(false)
    var isUpdateModalBottomVisible: StateFlow<Boolean> = _isUpdateModalBottomVisible


    //setter
    fun setName(name: String) {
        _name.value = name
    }

    fun setPhone(phone: String) {
        _phone.value = phone
    }

    fun setAddress(address: String) {
        _address.value = address
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setNameError(hasError: Boolean) {
        _nameError.value = hasError
    }

    fun setPhoneError(hasError: Boolean) {
        _phoneError.value = hasError
    }

    fun setAddressError(hasError: Boolean) {
        _addressError.value = hasError
    }

    fun setEmailError(hasError: Boolean) {
        _emailError.value = hasError
    }

    fun setSearch(search: String) {
        _search.value = search
    }

    fun setAddBottomVisible(isShow: Boolean) {
        _isAddModalBottomVisible.value = isShow
    }

    fun setUpdateBottomVisible(isShow: Boolean) {
        _isUpdateModalBottomVisible.value = isShow
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    fun addContact(contact: Contact) {
        contactRepository.addContact(contact)
        _contactList.value = contactRepository.getAllContact()//updateListState
    }

    fun updateContact(index: Int, newContact: Contact) {
        contactRepository.updateContact(index, newContact)
        _contactList.value = contactRepository.getAllContact()
    }

    fun deleteContact(index: Int) {
        contactRepository.deleteContact(index)
        _contactList.value = contactRepository.getAllContact()
    }


}