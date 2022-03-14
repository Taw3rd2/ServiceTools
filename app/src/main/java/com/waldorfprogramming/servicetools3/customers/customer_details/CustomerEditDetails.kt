package com.waldorfprogramming.servicetools3.customers.customer_details

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.waldorfprogramming.servicetools3.components.ServiceToolsTopBar
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme
import com.waldorfprogramming.servicetools3.utilities.createCustomerMap
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
class CustomerEditDetails: ComponentActivity() {

    private var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityFinisher: () -> Unit = {
            this.finish()
        }

        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getString("UpdateCustomerId").toString()
        }

        val viewModel: CustomerDetailsViewModel by viewModels()

        setContent {
            ServiceTools3Theme {

                var editableFirstName by remember { mutableStateOf("") }
                var editableLastName by remember { mutableStateOf("") }
                var editableStreetAddress by remember { mutableStateOf("") }
                var editableCity by remember { mutableStateOf("") }
                var editableState by remember { mutableStateOf("MI") }
                var editableZip by remember { mutableStateOf("") }
                var editablePhoneName by remember { mutableStateOf("") }
                var editablePhoneNumber by remember { mutableStateOf("") }
                var editableAlternatePhoneName by remember { mutableStateOf("") }
                var editableAlternatePhoneNumber by remember { mutableStateOf("") }
                var editableOtherPhoneName by remember { mutableStateOf("") }
                var editableOtherPhoneNumber by remember { mutableStateOf("") }
                var editableSquareFootage by remember { mutableStateOf("") }
                var editableIsCommercial by remember { mutableStateOf(false) }
                var editableNoService by remember { mutableStateOf(false) }

                if (viewModel.customerState.value.firstname.isNotEmpty()) {
                    editableFirstName = viewModel.customerState.value.firstname
                }
                if (viewModel.customerState.value.lastname.isNotEmpty()) {
                    editableLastName = viewModel.customerState.value.lastname
                }
                if (viewModel.customerState.value.street.isNotEmpty()) {
                    editableStreetAddress = viewModel.customerState.value.street
                }
                if (viewModel.customerState.value.city.isNotEmpty()) {
                    editableCity = viewModel.customerState.value.city
                }
                if (viewModel.customerState.value.state.isNotEmpty()) {
                    editableState = viewModel.customerState.value.state
                }
                if (viewModel.customerState.value.zip.isNotEmpty()) {
                    editableZip = viewModel.customerState.value.zip
                }
                if (viewModel.customerState.value.phoneName.isNotEmpty()) {
                    editablePhoneName = viewModel.customerState.value.phoneName
                }
                if (viewModel.customerState.value.phone.isNotEmpty()) {
                    editablePhoneNumber = viewModel.customerState.value.phone
                }
                if (viewModel.customerState.value.altPhoneName.isNotEmpty()) {
                    editableAlternatePhoneName = viewModel.customerState.value.altPhoneName
                }
                if (viewModel.customerState.value.altphone.isNotEmpty()) {
                    editableAlternatePhoneNumber = viewModel.customerState.value.altphone
                }
                if (viewModel.customerState.value.otherPhoneName.isNotEmpty()) {
                    editableOtherPhoneName = viewModel.customerState.value.otherPhoneName
                }
                if (viewModel.customerState.value.otherPhone.isNotEmpty()) {
                    editableOtherPhoneNumber = viewModel.customerState.value.otherPhone
                }
                if (viewModel.customerState.value.squarefootage.isNotEmpty()) {
                    editableSquareFootage = viewModel.customerState.value.squarefootage
                }
                editableNoService = viewModel.customerState.value.noService
                editableIsCommercial = viewModel.customerState.value.iscommercial

                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                val scrollState = rememberScrollState()
                val keyboardController = LocalSoftwareKeyboardController.current

                Scaffold(
                    topBar = {
                        ServiceToolsTopBar(title = "Edit Customer Details")
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
                        Row(modifier = Modifier.padding(top = 32.dp)) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.5f),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (editableNoService) {
                                    Text(
                                        text = "No Service",
                                        style = MaterialTheme.typography.caption,
                                        color = Color.Red
                                    )
                                } else {
                                    Text(
                                        text = "No Service",
                                        style = MaterialTheme.typography.caption,
                                        color = Color.Black
                                    )
                                }

                                Switch(
                                    modifier = Modifier
                                        .scale(1.5f)
                                        .padding(top = 8.dp),
                                    checked = editableNoService,
                                    onCheckedChange = { editableNoService = it },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.Red,
                                        checkedTrackColor = Color.Red,
                                    )
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.5f),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (editableIsCommercial) {
                                    Text(
                                        text = "Commercial",
                                        style = MaterialTheme.typography.caption,
                                        color = Color.Green
                                    )
                                } else {
                                    Text(
                                        text = "Commercial",
                                        style = MaterialTheme.typography.caption,
                                        color = Color.Black
                                    )
                                }
                                Switch(
                                    modifier = Modifier
                                        .scale(1.5f)
                                        .padding(top = 8.dp),
                                    checked = editableIsCommercial,
                                    onCheckedChange = { editableIsCommercial = it },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = MaterialTheme.colors.primary,
                                        checkedTrackColor = MaterialTheme.colors.primary
                                    )
                                )
                            }
                        }

                        if (editableIsCommercial) {
                            // Business Name
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 16.dp, top = 16.dp, end = 16.dp
                                    ),
                                value = editableLastName,
                                onValueChange = {
                                    editableLastName = it
                                },
                                label = { Text(text = "Business Name") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next,
                                    capitalization = KeyboardCapitalization.Words
                                ),
                                singleLine = true,
                                leadingIcon = {
                                    Icon(Icons.Filled.Edit, contentDescription = "edit")
                                }
                            )
                        } else {
                            //First Name
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 16.dp, top = 40.dp, end = 16.dp
                                    ),
                                value = editableFirstName,
                                onValueChange = {
                                    editableFirstName = it
                                },
                                label = { Text(text = "First Name") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next,
                                    capitalization = KeyboardCapitalization.Words
                                ),
                                singleLine = true,
                                leadingIcon = {
                                    Icon(Icons.Filled.Edit, contentDescription = "edit")
                                }
                            )
                            //Last Name
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 16.dp, top = 16.dp, end = 16.dp
                                    ),
                                value = editableLastName,
                                onValueChange = {
                                    editableLastName = it
                                },
                                label = { Text(text = "Last name") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next,
                                    capitalization = KeyboardCapitalization.Words
                                ),
                                singleLine = true,
                                leadingIcon = {
                                    Icon(Icons.Filled.Edit, contentDescription = "edit")
                                }
                            )
                        }

                        // Street Address
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableStreetAddress,
                            onValueChange = {
                                editableStreetAddress = it
                            },
                            label = { Text(text = "Street Address") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )
                        // City
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableCity,
                            onValueChange = {
                                editableCity = it
                            },
                            label = { Text(text = "City") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        // State
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableState,
                            onValueChange = {
                                editableState = it
                            },
                            label = { Text(text = "State") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Characters
                            ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )
                        // Zip Code
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableZip,
                            onValueChange = {
                                editableZip = it
                            },
                            label = { Text(text = "Zip Code") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next,
                            ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        // Primary Phone Name
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editablePhoneName,
                            onValueChange = {
                                editablePhoneName = it
                            },
                            label = { Text(text = "Primary Phone Name") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )
                        // Primary Phone Number
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editablePhoneNumber,
                            onValueChange = {
                                editablePhoneNumber = it
                            },
                            label = { Text(text = "Primary Phone Number") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next,
                            ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        // Alternate Phone Name
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableAlternatePhoneName,
                            onValueChange = {
                                editableAlternatePhoneName = it
                            },
                            label = { Text(text = "Alternate Phone Name") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )
                        // Alternate Phone Number
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableAlternatePhoneNumber,
                            onValueChange = {
                                editableAlternatePhoneNumber = it
                            },
                            label = { Text(text = "Alternate Phone Number") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next,
                            ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        // Other Phone Name
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableOtherPhoneName,
                            onValueChange = {
                                editableOtherPhoneName = it
                            },
                            label = { Text(text = "Other Phone Name") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )
                        // Other Phone Number
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableOtherPhoneNumber,
                            onValueChange = {
                                editableOtherPhoneNumber = it
                            },
                            label = { Text(text = "Other Phone Number") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next,
                            ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        if (editableIsCommercial) {
                            // Square footage
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 16.dp, top = 16.dp, end = 16.dp
                                    ),
                                value = editableSquareFootage,
                                onValueChange = {
                                    editableSquareFootage = it
                                },
                                label = { Text(text = "Square Footage of space served") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done,
                                ),
                                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                                singleLine = true,
                                leadingIcon = {
                                    Icon(Icons.Filled.Edit, contentDescription = "edit")
                                }
                            )
                        } else {
                            // Square footage
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 16.dp, top = 16.dp, end = 16.dp
                                    ),
                                value = editableSquareFootage,
                                onValueChange = {
                                    editableSquareFootage = it
                                },
                                label = { Text(text = "Square Footage") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done,
                                ),
                                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                                singleLine = true,
                                leadingIcon = {
                                    Icon(Icons.Filled.Edit, contentDescription = "edit")
                                }
                            )
                        }

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 40.dp, end = 16.dp, bottom = 72.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                            onClick = {
                                if (editableLastName.isEmpty()) {
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar("You cant delete the last name or Business Name")
                                    }
                                } else {
                                    val mappedCustomer = createCustomerMap(
                                        editableFirstName,
                                        editableLastName,
                                        editableStreetAddress,
                                        editableCity,
                                        editableState,
                                        editableZip,
                                        editablePhoneName,
                                        editablePhoneNumber,
                                        editableAlternatePhoneName,
                                        editableAlternatePhoneNumber,
                                        editableOtherPhoneName,
                                        editableOtherPhoneNumber,
                                        editableSquareFootage,
                                        editableIsCommercial,
                                        editableNoService
                                    )
                                    val basicDb = FirebaseFirestore.getInstance()
                                    val documentReference = basicDb
                                        .collection("customers")
                                        .document(id)
                                    documentReference
                                        .set(mappedCustomer, SetOptions.merge())
                                        .addOnSuccessListener {
                                            scope.launch {
                                                scaffoldState.snackbarHostState.showSnackbar("Updated")
                                                activityFinisher()
                                            }
                                        }
                                        .addOnFailureListener {
                                            scope.launch {
                                                scaffoldState.snackbarHostState.showSnackbar("Failed")
                                                activityFinisher()
                                            }
                                        }
                                }
                            }
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = "Update",
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