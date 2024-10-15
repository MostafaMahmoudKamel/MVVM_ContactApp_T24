package com.example.mvvm_contactapp_t24.repository

import com.example.mvvm_contactapp_t24.model.Contact

interface ContactRepository {
    //    private var contactList:MutableList<Contact>
    fun addContact(contact: Contact)
    fun getAllContact(): List<Contact>
    fun updateContact(index: Int, newContact: Contact)
    fun deleteContact(index: Int)
    fun searchContact(search: String): List<Contact>


}