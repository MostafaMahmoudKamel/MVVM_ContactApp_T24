package com.example.mvvm_contactapp_t24.repository

import com.example.mvvm_contactapp_t24.model.Contact

interface ContactRepository {
    suspend fun addContact(contact: Contact)
    suspend fun getAllContact(): List<Contact>
    suspend fun updateContact(index: Int, newContact: Contact)
    suspend fun deleteContact(index: Int)
    suspend fun searchContact(search: String): List<Contact>
}