package com.waldorfprogramming.servicetools3.customers.equipment.equipment_details

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
import com.waldorfprogramming.servicetools3.utilities.createCustomerEquipmentUpdateMap
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
class EquipmentEditDetails: ComponentActivity() {

    private var customerId = ""
    private var equipmentId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityFinisher: () -> Unit = {
            this.finish()
        }

        val bundle = intent.extras
        if (bundle != null) {
            customerId = bundle.getString("CustomerId").toString()
            equipmentId = bundle.getString("EquipmentId").toString()
        }

        val viewModel: EquipmentDetailsViewModel by viewModels()

        setContent {
            ServiceTools3Theme {

                var editableEquipmentBrand by remember { mutableStateOf("") }
                var editableEquipmentBtu by remember { mutableStateOf("") }
                var editableEquipmentEfficiency by remember { mutableStateOf("") }
                var editableEquipmentFuel by remember { mutableStateOf("") }
                var editableEquipmentModel by remember { mutableStateOf("") }
                var editableEquipmentName by remember { mutableStateOf("") }
                var editableEquipmentSerial by remember { mutableStateOf("") }
                var editableEquipmentVoltage by remember { mutableStateOf("") }
                var editableEquipmentKey by remember { mutableStateOf("") }

                if(viewModel.equipmentState.value.equipmentBrand.isNotEmpty()){
                    editableEquipmentBrand = viewModel.equipmentState.value.equipmentBrand
                }
                if(viewModel.equipmentState.value.equipmentBtu.isNotEmpty()){
                    editableEquipmentBtu = viewModel.equipmentState.value.equipmentBtu
                }
                if(viewModel.equipmentState.value.equipmentEff.isNotEmpty()){
                    editableEquipmentEfficiency = viewModel.equipmentState.value.equipmentEff
                }
                if(viewModel.equipmentState.value.equipmentFuel.isNotEmpty()){
                    editableEquipmentFuel = viewModel.equipmentState.value.equipmentFuel
                }
                if(viewModel.equipmentState.value.equipmentModel.isNotEmpty()){
                    editableEquipmentModel = viewModel.equipmentState.value.equipmentModel
                }
                if(viewModel.equipmentState.value.equipmentName.isNotEmpty()){
                    editableEquipmentName = viewModel.equipmentState.value.equipmentName
                }
                if(viewModel.equipmentState.value.equipmentSerial.isNotEmpty()){
                    editableEquipmentSerial = viewModel.equipmentState.value.equipmentSerial
                }
                if(viewModel.equipmentState.value.equipmentVoltage.isNotEmpty()){
                    editableEquipmentVoltage = viewModel.equipmentState.value.equipmentVoltage
                }
                if(viewModel.equipmentState.value.key.isNotEmpty()){
                    editableEquipmentKey = viewModel.equipmentState.value.key
                }

                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                val scrollState = rememberScrollState()
                val keyboardController = LocalSoftwareKeyboardController.current

                Scaffold(
                    topBar = {
                        ServiceToolsTopBar(title = "Edit Equipment")
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

                        //Equipment Name
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 40.dp, end = 16.dp
                                ),
                            value = editableEquipmentName,
                            onValueChange = { editableEquipmentName = it },
                            label = { Text(text = "Equipment Name") },
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

                        //Equipment Brand
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableEquipmentBrand,
                            onValueChange = {
                                editableEquipmentBrand = it
                            },
                            label = { Text(text = "Equipment Brand")},
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

                        //Equipment Model
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableEquipmentModel,
                            onValueChange = {
                                editableEquipmentModel = it
                            },
                            label = { Text(text = "Equipment Model")},
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

                        //Equipment Serial
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableEquipmentSerial,
                            onValueChange = {
                                editableEquipmentSerial = it
                            },
                            label = { Text(text = "Equipment Serial")},
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

                        //Equipment Efficiency
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableEquipmentEfficiency,
                            onValueChange = {
                                editableEquipmentEfficiency = it
                            },
                            label = { Text(text = "Equipment Efficiency")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                            ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        //Equipment Fuel
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableEquipmentFuel,
                            onValueChange = {
                                editableEquipmentFuel = it
                            },
                            label = { Text(text = "Equipment Fuel")},
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

                        //Equipment Btu's
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableEquipmentBtu,
                            onValueChange = {
                                editableEquipmentBtu = it
                            },
                            label = { Text(text = "Equipment Btu's")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next,
                            ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                        )

                        //Equipment Voltage
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableEquipmentVoltage,
                            onValueChange = {
                                editableEquipmentVoltage = it
                            },
                            label = { Text(text = "Equipment Voltage")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
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
                                if(editableEquipmentName.isEmpty()){
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar("Equipment Name is Required")
                                    }
                                } else {
                                    editableEquipmentKey = editableEquipmentName
                                    val mappedEquipment = createCustomerEquipmentUpdateMap(
                                        customerId,
                                        editableEquipmentBrand,
                                        editableEquipmentBtu,
                                        editableEquipmentEfficiency,
                                        editableEquipmentFuel,
                                        editableEquipmentModel,
                                        editableEquipmentName,
                                        editableEquipmentSerial,
                                        editableEquipmentVoltage,
                                        editableEquipmentKey
                                    )
                                    val basicDb = FirebaseFirestore.getInstance()
                                    val documentReference = basicDb
                                        .collection("customers")
                                        .document(customerId)
                                        .collection("Equipment")
                                        .document(equipmentId)
                                    documentReference
                                        .set(mappedEquipment, SetOptions.merge())
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