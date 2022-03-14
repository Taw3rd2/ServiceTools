package com.waldorfprogramming.servicetools3.customers.customer_create

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.waldorfprogramming.servicetools3.components.ServiceToolsTopBar
import com.waldorfprogramming.servicetools3.components.StandardCreateInput
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme
import com.waldorfprogramming.servicetools3.utilities.createCustomerMap
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
class CustomerCreate: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityKiller: () -> Unit = {
            this.finish()
        }

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

                    val scaffoldState = rememberScaffoldState()
                    val scope = rememberCoroutineScope()
                    val scrollState = rememberScrollState()

                    Scaffold(
                        topBar = {
                            ServiceToolsTopBar(title = "Create New Customer")
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

                            //Name
                            if (!editableIsCommercial) {
                                //First Name
                                StandardCreateInput(
                                    textInput = editableFirstName,
                                    label = "First Name",
                                    onTextChange = { editableFirstName = it },
                                    topSpace = 40
                                )
                                //LastName
                                StandardCreateInput(
                                    textInput = editableLastName,
                                    label = "Last Name",
                                    onTextChange = { editableLastName = it },
                                    topSpace = 16
                                )
                            } else {
                                //Business Name
                                StandardCreateInput(
                                    textInput = editableLastName,
                                    label = "Business Name",
                                    onTextChange = { editableLastName = it },
                                    topSpace = 40
                                )
                            }

                            //Street Address
                            StandardCreateInput(
                                textInput = editableStreetAddress,
                                label = "Street Address",
                                onTextChange = { editableStreetAddress = it },
                                topSpace = 16
                            )

                            //City
                            StandardCreateInput(
                                textInput = editableCity,
                                label = "City",
                                onTextChange = { editableCity = it },
                                topSpace = 16
                            )

                            //State
                            StandardCreateInput(
                                textInput = editableState,
                                label = "State",
                                onTextChange = { editableState = it },
                                topSpace = 16
                            )

                            //Zip Code
                            StandardCreateInput(
                                textInput = editableZip,
                                label = "Zip Code",
                                onTextChange = { editableZip = it },
                                topSpace = 16,
                                keyboardType = KeyboardType.Number
                            )

                            //Primary Phone Name
                            StandardCreateInput(
                                textInput = editablePhoneName,
                                label = "Primary Contact Name",
                                onTextChange = { editablePhoneName = it },
                                topSpace = 16
                            )

                            //Primary Phone Number
                            StandardCreateInput(
                                textInput = editablePhoneNumber,
                                label = "Primary Phone Number",
                                onTextChange = { editablePhoneNumber = it },
                                topSpace = 16,
                                keyboardType = KeyboardType.Number
                            )

                            //Alternate Phone Name
                            StandardCreateInput(
                                textInput = editableAlternatePhoneName,
                                label = "Alternate Contact Name",
                                onTextChange = { editableAlternatePhoneName = it },
                                topSpace = 16
                            )

                            //Alternate Phone Number
                            StandardCreateInput(
                                textInput = editableAlternatePhoneNumber,
                                label = "Alternate Phone Number",
                                onTextChange = { editableAlternatePhoneNumber = it },
                                topSpace = 16,
                                keyboardType = KeyboardType.Number
                            )

                            //Other Phone Name
                            StandardCreateInput(
                                textInput = editableOtherPhoneName,
                                label = "Other Contact Name",
                                onTextChange = { editableOtherPhoneName = it },
                                topSpace = 16
                            )

                            //Other Phone Number
                            StandardCreateInput(
                                textInput = editableOtherPhoneNumber,
                                label = "Other Phone Number",
                                onTextChange = { editableOtherPhoneNumber = it },
                                topSpace = 16,
                                keyboardType = KeyboardType.Number
                            )

                            //Square Footage
                            if (!editableIsCommercial) {
                                StandardCreateInput(
                                    textInput = editableSquareFootage,
                                    label = "Square Footage",
                                    onTextChange = { editableSquareFootage = it },
                                    topSpace = 16,
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                )
                            } else {
                                StandardCreateInput(
                                    textInput = editableSquareFootage,
                                    label = "Square Footage of area served",
                                    onTextChange = { editableSquareFootage = it },
                                    topSpace = 16,
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                )
                            }

                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 16.dp,
                                        top = 40.dp,
                                        end = 16.dp,
                                        bottom = 72.dp
                                    ),
                                shape = RoundedCornerShape(5.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                                onClick = {
                                    if (editableLastName.isEmpty()) {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar("Last name or Business name required")
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

                                        documentReference
                                            .add(mappedCustomer)
                                            .addOnSuccessListener {
                                                scope.launch {
                                                    scaffoldState.snackbarHostState.showSnackbar("Created")
                                                    activityKiller()
                                                }
                                            }
                                            .addOnFailureListener {
                                                scope.launch {
                                                    scaffoldState.snackbarHostState.showSnackbar("Failed")
                                                    activityKiller()
                                                }
                                            }
                                    }
                                }
                            ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = "Create",
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