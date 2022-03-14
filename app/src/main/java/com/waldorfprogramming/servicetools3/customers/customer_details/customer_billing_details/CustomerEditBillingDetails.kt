package com.waldorfprogramming.servicetools3.customers.customer_details.customer_billing_details

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
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
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.waldorfprogramming.servicetools3.components.ServiceToolsTopBar
import com.waldorfprogramming.servicetools3.customers.customer_details.CustomerDetailsViewModel
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme
import com.waldorfprogramming.servicetools3.utilities.createCustomerBillingMap
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
class CustomerEditBillingDetails: ComponentActivity() {

    private var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getString("UpdateCustomerId").toString()
        }

        val viewModel: CustomerDetailsViewModel by viewModels()

        setContent {
            ServiceTools3Theme {
                var editableBillingOrganization by remember { mutableStateOf("")}
                var editableBillingPrimaryName by remember { mutableStateOf("")}
                var editableBillingPrimaryPhone by remember { mutableStateOf("")}
                var editableBillingPrimaryEmail by remember { mutableStateOf("")}
                var editableBillingAlternateName by remember { mutableStateOf("")}
                var editableBillingAlternatePhone by remember { mutableStateOf("")}
                var editableBillingAlternateEmail by remember { mutableStateOf("")}
                var editableBillingOtherName by remember { mutableStateOf("")}
                var editableBillingOtherPhone by remember { mutableStateOf("")}
                var editableBillingOtherEmail by remember { mutableStateOf("")}
                var editableBillingStreet by remember { mutableStateOf("")}
                var editableBillingCity by remember { mutableStateOf("")}
                var editableBillingState by remember { mutableStateOf("")}
                var editableBillingZip by remember { mutableStateOf("")}
                var editableIsCommercial by remember { mutableStateOf(false)}
                var editableNoService by remember { mutableStateOf(false)}

                if(viewModel.customerState.value.billingorg.isNotEmpty()) {
                    editableBillingOrganization = viewModel.customerState.value.billingorg
                }

                if(viewModel.customerState.value.billingPrimaryName.isNotEmpty()) {
                    editableBillingPrimaryName = viewModel.customerState.value.billingPrimaryName
                }

                if(viewModel.customerState.value.billingPrimaryPhone.isNotEmpty()) {
                    editableBillingPrimaryPhone = viewModel.customerState.value.billingPrimaryPhone
                }

                if(viewModel.customerState.value.billingPrimaryEmail.isNotEmpty()) {
                    editableBillingPrimaryEmail = viewModel.customerState.value.billingPrimaryEmail
                }

                if(viewModel.customerState.value.billingAlternateName.isNotEmpty()) {
                    editableBillingAlternateName = viewModel.customerState.value.billingAlternateName
                }

                if(viewModel.customerState.value.billingAlternatePhone.isNotEmpty()) {
                    editableBillingAlternatePhone = viewModel.customerState.value.billingAlternatePhone
                }

                if(viewModel.customerState.value.billingAlternateEmail.isNotEmpty()) {
                    editableBillingAlternateEmail = viewModel.customerState.value.billingAlternateEmail
                }

                if(viewModel.customerState.value.billingOtherName.isNotEmpty()) {
                    editableBillingOtherName = viewModel.customerState.value.billingOtherName
                }

                if(viewModel.customerState.value.billingOtherPhone.isNotEmpty()) {
                    editableBillingOtherPhone = viewModel.customerState.value.billingOtherPhone
                }

                if(viewModel.customerState.value.billingOtherEmail.isNotEmpty()) {
                    editableBillingOtherEmail = viewModel.customerState.value.billingOtherEmail
                }

                if(viewModel.customerState.value.billingstreet.isNotEmpty()) {
                    editableBillingStreet = viewModel.customerState.value.billingstreet
                }

                if(viewModel.customerState.value.billingcity.isNotEmpty()) {
                    editableBillingCity = viewModel.customerState.value.billingcity
                }

                if(viewModel.customerState.value.billingstate.isNotEmpty()) {
                    editableBillingState = viewModel.customerState.value.billingstate
                }

                if(viewModel.customerState.value.billingzip.isNotEmpty()) {
                    editableBillingZip = viewModel.customerState.value.billingzip
                }

                editableNoService = viewModel.customerState.value.noService
                editableIsCommercial = viewModel.customerState.value.iscommercial

                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                val bringIntoViewRequester = remember { BringIntoViewRequester() }
                val keyboardController = LocalSoftwareKeyboardController.current

                Scaffold (
                    topBar = {
                        ServiceToolsTopBar(title = "Edit Customer Billing Details")
                    },
                    scaffoldState = scaffoldState
                        ){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
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

                        // Business name
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableBillingOrganization,
                            onValueChange = {
                                editableBillingOrganization = it
                            },
                            label = { Text(text = "Business/Organization Name")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide()} ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        //Primary Contact Name
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableBillingPrimaryName,
                            onValueChange = {
                                editableBillingPrimaryName = it
                            },
                            label = { Text(text = "Primary Contact Name")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide()} ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        //Primary Contact Phone
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableBillingPrimaryPhone,
                            onValueChange = {
                                editableBillingPrimaryPhone = it
                            },
                            label = { Text(text = "Primary Contact Phone")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide()} ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        //Primary Contact Email
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableBillingPrimaryEmail,
                            onValueChange = {
                                editableBillingPrimaryEmail = it
                            },
                            label = { Text(text = "Primary Contact Email")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide()} ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        //Alternate Contact Name
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableBillingAlternateName,
                            onValueChange = {
                                editableBillingAlternateName = it
                            },
                            label = { Text(text = "Alternate Contact Name")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide()} ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        //Alternate Contact Phone
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableBillingAlternatePhone,
                            onValueChange = {
                                editableBillingAlternatePhone = it
                            },
                            label = { Text(text = "Alternate Contact Phone")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide()} ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        //Alternate Contact Email
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableBillingAlternateEmail,
                            onValueChange = {
                                editableBillingAlternateEmail = it
                            },
                            label = { Text(text = "Alternate Contact Email")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide()} ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        //Other Contact Name
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableBillingOtherName,
                            onValueChange = {
                                editableBillingOtherName = it
                            },
                            label = { Text(text = "Other Contact Name")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide()} ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        //Other Contact Phone
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableBillingOtherPhone,
                            onValueChange = {
                                editableBillingOtherPhone = it
                            },
                            label = { Text(text = "Other Contact Phone")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide()} ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        //Other Contact Email
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableBillingOtherEmail,
                            onValueChange = {
                                editableBillingOtherEmail = it
                            },
                            label = { Text(text = "Primary Contact Name")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide()} ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        //Billing Street Address
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableBillingStreet,
                            onValueChange = {
                                editableBillingStreet = it
                            },
                            label = { Text(text = "Billing Street Address")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide()} ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        //Billing Address City
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableBillingCity,
                            onValueChange = {
                                editableBillingCity = it
                            },
                            label = { Text(text = "Billing Address City")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide()} ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        //Billing Address State
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableBillingState,
                            onValueChange = {
                                editableBillingState = it
                            },
                            label = { Text(text = "Billing Address State")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide()} ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        //Billing Address Zip
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableBillingZip,
                            onValueChange = {
                                editableBillingZip = it
                            },
                            label = { Text(text = "Billing Address Zip Code")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide()} ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 40.dp, end = 16.dp, bottom = 72.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                            onClick = {
                                val mappedCustomerBillingInformation = createCustomerBillingMap(
                                    editableBillingOrganization,
                                    editableBillingPrimaryName,
                                    editableBillingPrimaryPhone,
                                    editableBillingPrimaryEmail,
                                    editableBillingAlternateName,
                                    editableBillingAlternatePhone,
                                    editableBillingAlternateEmail,
                                    editableBillingOtherName,
                                    editableBillingOtherPhone,
                                    editableBillingOtherEmail,
                                    editableBillingStreet,
                                    editableBillingCity,
                                    editableBillingState,
                                    editableBillingZip,
                                    editableIsCommercial,
                                    editableNoService
                                )
                                val basicDb = FirebaseFirestore.getInstance()
                                val documentReference = basicDb
                                    .collection("customers")
                                    .document(id)
                                documentReference
                                    .set(mappedCustomerBillingInformation, SetOptions.merge())
                                    .addOnSuccessListener {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar("Updated")
                                            finish()
                                        }
                                    }
                                    .addOnFailureListener {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar("Failed")
                                            finish()
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