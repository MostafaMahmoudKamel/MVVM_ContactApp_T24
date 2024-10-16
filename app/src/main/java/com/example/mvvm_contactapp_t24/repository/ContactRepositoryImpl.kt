package com.example.mvvm_contactapp_t24.repository

import com.example.mvvm_contactapp_t24.model.Contact

class ContactRepositoryImpl : ContactRepository {

    private val contactList: MutableList<Contact> = mutableListOf()

    override fun addContact(contact: Contact) {
        contactList.add(contact)
    }

    override fun getAllContact(): List<Contact> {
        return contactList.toList()
    }

    override fun updateContact(index: Int, newContact: Contact) {
        contactList[index] = newContact
    }

    override fun deleteContact(index: Int) {
        contactList.removeAt(index)
    }

    override fun searchContact(search: String): List<Contact> {
        return contactList.filter { it.name.startsWith(search, ignoreCase = true) }
    }
}