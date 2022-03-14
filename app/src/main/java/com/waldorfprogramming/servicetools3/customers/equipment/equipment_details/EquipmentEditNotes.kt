package com.waldorfprogramming.servicetools3.customers.equipment.equipment_details

import android.os.Bundle
import android.util.Log
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.firestore.FirebaseFirestore
import com.waldorfprogramming.servicetools3.components.ServiceToolsTopBar
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalComposeUiApi
class EquipmentEditNotes: ComponentActivity() {

    private var customerId: String = ""
    private var equipmentId: String = ""
    private var notes: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        if (bundle != null) {
            customerId = bundle.getString("customerId").toString()
            equipmentId = bundle.getString("equipmentId").toString()
            notes = bundle.getString("equipmentNotes").toString()

        }

        val activityFinisher: () -> Unit = {
            this.finish()
        }

        setContent {
            ServiceTools3Theme {

                var editableNotes by remember { mutableStateOf("") }
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                val scrollState = rememberScrollState()

                if (notes.isNotEmpty()) {
                    editableNotes = notes
                }

                Scaffold(
                    topBar = {
                        ServiceToolsTopBar(title = "Add Equipment Notes")
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
                                    .document(customerId)
                                    .collection("Equipment")
                                    .document(equipmentId)

                                customerNoteDocumentReference
                                    .update("equipmentNotes", notesToAdd)
                                    .addOnSuccessListener {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar("Updated")
                                            activityFinisher()
                                        }
                                    }
                                    .addOnFailureListener {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar("Failed..")
                                            activityFinisher()
                                        }
                                    }
                                activityFinisher()
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