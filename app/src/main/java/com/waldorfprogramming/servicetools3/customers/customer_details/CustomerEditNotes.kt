package com.waldorfprogramming.servicetools3.customers.customer_details

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.waldorfprogramming.servicetools3.components.ServiceToolsTopBar
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme

@ExperimentalComposeUiApi
class CustomerEditNotes: ComponentActivity() {

    private var id: String = ""
    private var cnotes: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getString("UpdateCustomerId").toString()
            cnotes = bundle.getString("UpdateCustomerNotes").toString()
        }

        setContent {
            ServiceTools3Theme {

                var editableNotes by remember { mutableStateOf("") }
                val scaffoldState = rememberScaffoldState()
                val scrollState = rememberScrollState()
                val keyboardController = LocalSoftwareKeyboardController.current

                if (cnotes.isNotEmpty()) {
                    editableNotes = cnotes
                }

                Scaffold(
                    topBar = {
                        ServiceToolsTopBar(title = "Add Customer Notes")
                    },
                    scaffoldState = scaffoldState
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 40.dp, end = 16.dp
                                ),
                            value = editableNotes,
                            onValueChange = { editableNotes = it },
                            label = { Text(text = "Notes") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done,
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { keyboardController?.hide()}
                            ),
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "Add Notes")
                            }
                        )


                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 40.dp, end = 16.dp, bottom = 72.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                            onClick = {
                                val notesToAdd: String = editableNotes
                                val noteDb = FirebaseFirestore.getInstance()
                                val customerNoteDocumentReference = noteDb
                                    .collection("customers")
                                    .document(id)
                                customerNoteDocumentReference.update("cnotes", notesToAdd)
                                finish()
                            }
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = "Add",
                                color = Color.White,
                                style = MaterialTheme.typography.button,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}