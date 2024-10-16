package com.example.mvvm_contactapp_t24.repository

import com.example.mvvm_contactapp_t24.model.Contact
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay

class ContactRepositoryImpl : ContactRepository {

    private val contactList: MutableList<Contact> = mutableListOf()

    override suspend fun addContact(contact: Contact) {
//        Thread.sleep(2000) //pause Ui
        delay(1000)//not pause Ui
        contactList.add(contact)
    }

    override suspend fun getAllContact(): List<Contact> {
        delay(1000)

        return contactList.toList()
    }

    override suspend fun updateContact(index: Int, newContact: Contact) {
        delay(1000)

        contactList[index] = newContact
    }

    override suspend fun deleteContact(index: Int) {
        delay(1000)

        contactList.removeAt(index)
    }

    override suspend fun searchContact(search: String): List<Contact> {
        delay(1000)

        return contactList.filter { it.name.startsWith(search, ignoreCase = true) }
    }
}