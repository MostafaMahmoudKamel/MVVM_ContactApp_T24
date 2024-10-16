package com.example.mvvm_contactapp_t24.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mvvm_contactapp_t24.model.Contact
import com.example.mvvm_contactapp_t24.ui.layout.ContactCard
import com.example.mvvm_contactapp_t24.ui.layout.UIState
import com.example.mvvm_contactapp_t24.utils.isEmailValid
import com.example.mvvm_contactapp_t24.utils.isNameValid
import com.example.mvvm_contactapp_t24.utils.isPhoneValid
import com.example.mvvm_contactapp_t24.viewModel.ContactViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen() {
    //viewModel
    val contactViewModel: ContactViewModel = viewModel()

    //Inputs
    val name by contactViewModel.name.collectAsState()
    val phone by contactViewModel.phone.collectAsState()
    val address by contactViewModel.address.collectAsState()
    val email by contactViewModel.email.collectAsState()

    //errors
    val nameError by contactViewModel.nameError.collectAsState()
    val phoneError by contactViewModel.phoneError.collectAsState()
    val addressError by contactViewModel.addressError.collectAsState()
    val emailError by contactViewModel.emailError.collectAsState()

    //visability
    val isAddBottomVisible by contactViewModel.isAddModalBottomVisible.collectAsState()
    val isUpdateBottomVisible by contactViewModel.isUpdateModalBottomVisible.collectAsState()

    //list
    val contactList by contactViewModel.contactList.collectAsState()

    val uiState by contactViewModel.uiState.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contact List", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Magenta)

            )

        },
        snackbarHost = {},
        floatingActionButton = {
            FloatingActionButton(
                onClick = { contactViewModel.setAddBottomVisible(isShow = true) },
                containerColor = Color.Magenta
            ) { Icon(imageVector = Icons.Default.Add, contentDescription = null) }
        }

    ) { padding ->


        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(top = 16.dp)
        ) {
            //start
            when (uiState) {
                is UIState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color(0xFF6200EE))
                        }
                    }

                }

                is UIState.Success -> {
                    itemsIndexed(contactList, key = { index, _ -> index }) { index, contact ->
                        ContactCard(contact) {
                            contactViewModel.setUpdateBottomVisible(isShow = true)
                            contactViewModel.setIndex(index = index)

                            // Initialize text fields with the selected contact's data only when the card is clicked
                            contactViewModel.setName(contactList[contactViewModel.index.value].name)
                            contactViewModel.setPhone(contactList[contactViewModel.index.value].phone)
                            contactViewModel.setAddress(contactList[contactViewModel.index.value].address)
                            contactViewModel.setEmail(contactList[contactViewModel.index.value].email)
                        }
                    }
                }

                is UIState.Error -> {}
            }

        }



        if (isAddBottomVisible) {
            ModalBottomSheet(
                onDismissRequest = {
                    contactViewModel.setAddBottomVisible(isShow = false)
                },
                content = {
                    Column {
                        Text(
                            "Add Contact",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )
                        OutlinedTextField(
                            value = name,
                            onValueChange = {
                                contactViewModel.setName(it) //it
                                if (name.isNameValid()) contactViewModel.setNameError(false) else contactViewModel.setNameError(
                                    true
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .wrapContentHeight(),
                            label = { Text("name") },
                            isError = nameError
                        )
                        OutlinedTextField(
                            value = phone,
                            onValueChange = {
                                contactViewModel.setPhone(it)
                                if (phone.isPhoneValid()) contactViewModel.setPhoneError(false) else contactViewModel.setPhoneError(
                                    true
                                )

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .wrapContentHeight(),
                            label = { Text("phone") },
                            isError = phoneError

                        )
                        OutlinedTextField(
                            value = address,
                            onValueChange = {
                                contactViewModel.setAddress(it)

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .wrapContentHeight(),
                            label = { Text("address") },
                            isError = addressError

                        )
                        OutlinedTextField(
                            value = email,
                            onValueChange = {
                                contactViewModel.setEmail(it)
                                if (email.isEmailValid()) contactViewModel.setEmailError(false) else contactViewModel.setEmailError(
                                    true
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .wrapContentHeight(),
                            label = { Text("email") },
                            isError = emailError

                        )
                        Row(horizontalArrangement = Arrangement.SpaceAround) {
                            Button(
                                onClick = {
                                    contactViewModel.addContact(
                                        Contact(
                                            name,
                                            phone,
                                            address,
                                            email,
                                            "",
                                            ""
                                        )
                                    )
                                    contactViewModel.setAddBottomVisible(isShow = false)//hide add button
                                    //empty inputData in viewModel
                                    contactViewModel.setName("")
                                    contactViewModel.setEmail("")
                                    contactViewModel.setPhone("")
                                    contactViewModel.setAddress("")

                                }
                            ) {
                                Text("Confirm")
                            }
                            Button(onClick = { contactViewModel.setAddBottomVisible(isShow = false) }) {
                                Text(
                                    "Cancel"
                                )
                            }
                        }
                    }
                }
            )
        }

        if (isUpdateBottomVisible) {
            ModalBottomSheet(
                onDismissRequest = {
                    contactViewModel.setUpdateBottomVisible(isShow = false)
                },
                content = {
                    Column {
                        Text(
                            "Update Contact",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )

                        OutlinedTextField(
                            value = name,
                            onValueChange = { contactViewModel.setName(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .wrapContentHeight(),
                            label = { Text("name") }
                        )
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { contactViewModel.setPhone(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .wrapContentHeight(),
                            label = { Text("phone") }

                        )
                        OutlinedTextField(
                            value = address,
                            onValueChange = { contactViewModel.setAddress(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .wrapContentHeight(),
                            label = { Text("address") }
                        )
                        OutlinedTextField(
                            value = email,
                            onValueChange = { contactViewModel.setEmail(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .wrapContentHeight(),
                            label = { Text("email") }
                        )
                        Row(horizontalArrangement = Arrangement.SpaceAround) {
                            Button(onClick = {
                                contactViewModel.updateContact(
                                    index = contactViewModel.index.value,
                                    newContact = Contact(name, phone, address, email, "", "")
                                )
                                contactViewModel.setUpdateBottomVisible(isShow = false)

                            }) {
                                Text("Confirm")
                            }
                            Button(onClick = {
                                contactViewModel.setUpdateBottomVisible(isShow = false)
                                //empty inputData in viewModel
                                contactViewModel.setName("")
                                contactViewModel.setEmail("")
                                contactViewModel.setPhone("")
                                contactViewModel.setAddress("")
                            }) {
                                Text(
                                    "Cancel"
                                )
                            }
                            IconButton(onClick = {
                                contactViewModel.deleteContact(index = contactViewModel.index.value)
                                contactViewModel.setUpdateBottomVisible(isShow = false)
                            }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                            }
                        }
                    }

                }


            )
        }
    }
}

@Preview
@Composable
fun previewContact() {
    ContactScreen()
}