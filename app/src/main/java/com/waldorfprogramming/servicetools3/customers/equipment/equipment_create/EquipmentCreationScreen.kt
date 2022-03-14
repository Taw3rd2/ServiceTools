package com.waldorfprogramming.servicetools3.customers.equipment.equipment_create

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.waldorfprogramming.servicetools3.components.ServiceToolsTopBar
import com.waldorfprogramming.servicetools3.components.StandardCreateInput
import com.waldorfprogramming.servicetools3.utilities.createCustomerEquipmentMap
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun EquipmentCreationScreen(
    customerId: String,
    customerFirstName: String,
    customerLastName: String,
    activityKiller: () -> Unit
) {
    var editableEquipmentBrand by remember { mutableStateOf("")}
    var editableEquipmentBtu by remember {mutableStateOf("")}
    val equipmentContract = ""
    var editableEquipmentEfficiency by remember {mutableStateOf("")}
    var editableEquipmentFuel by remember {mutableStateOf("")}
    var editableEquipmentModel by remember {mutableStateOf("")}
    var editableEquipmentName by remember {mutableStateOf("")}
    val equipmentNotes = ""
    var editableEquipmentSerial by remember {mutableStateOf("")}
    var editableEquipmentVoltage by remember {mutableStateOf("")}
    val partsWarranty = ""
    val key = ""
    val laborWarranty = ""

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            ServiceToolsTopBar(title = "Create New Equipment")
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
            StandardCreateInput(
                textInput = editableEquipmentName,
                label = "Equipment Name",
                onTextChange = { editableEquipmentName = it },
                topSpace = 40,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            )

            //Equipment Brand
            StandardCreateInput(
                textInput = editableEquipmentBrand,
                label = "Equipment Brand",
                onTextChange = { editableEquipmentBrand = it },
                topSpace = 16,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            )

            //Equipment Model
            StandardCreateInput(
                textInput = editableEquipmentModel,
                label = "Equipment Model",
                onTextChange = { editableEquipmentModel = it },
                topSpace = 16,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Characters
            )

            //Equipment Serial
            StandardCreateInput(
                textInput = editableEquipmentSerial,
                label = "Equipment Serial",
                onTextChange = { editableEquipmentSerial = it },
                topSpace = 16,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Characters
            )

            //Equipment Efficiency
            StandardCreateInput(
                textInput = editableEquipmentEfficiency,
                label = "Equipment Efficiency",
                onTextChange = { editableEquipmentEfficiency = it },
                topSpace = 16,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences
            )

            //Equipment Fuel/Freon
            StandardCreateInput(
                textInput = editableEquipmentFuel,
                label = "Equipment Fuel or Freon",
                onTextChange = { editableEquipmentFuel = it },
                topSpace = 16,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences
            )

            //Equipment Voltage
            StandardCreateInput(
                textInput = editableEquipmentVoltage,
                label = "Equipment Voltage",
                onTextChange = { editableEquipmentVoltage = it },
                topSpace = 16,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences
            )

            //Equipment Btu's
            StandardCreateInput(
                textInput = editableEquipmentBtu,
                label = "Equipment Btu's",
                onTextChange = { editableEquipmentBtu = it },
                topSpace = 16,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Sentences
            )

            //Create Button
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 40.dp, end = 16.dp, bottom = 72.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                onClick = {
                    if (editableEquipmentBrand.isEmpty()) {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Brand Name is Required")
                        }
                    } else {
                        val mappedEquipment = createCustomerEquipmentMap(
                            customerId = customerId,
                            editableEquipmentBrand,
                            editableEquipmentBtu,
                            equipmentContract,
                            editableEquipmentEfficiency,
                            editableEquipmentFuel,
                            editableEquipmentModel,
                            editableEquipmentName,
                            equipmentNotes,
                            editableEquipmentSerial,
                            editableEquipmentVoltage,
                            partsWarranty,
                            key = key,
                            laborWarranty,
                        )
                        val basicDb = FirebaseFirestore.getInstance()
                        val documentReference = basicDb
                            .collection("customers")
                            .document(customerId)
                            .collection("Equipment")
                            .document(editableEquipmentName)

                        documentReference
                            .set(mappedEquipment)
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