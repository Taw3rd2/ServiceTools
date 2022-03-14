package com.waldorfprogramming.servicetools3.customers.equipment.equipment_details

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.waldorfprogramming.servicetools3.components.*
import com.waldorfprogramming.servicetools3.customers.customer_details.CustomerDetailsActivity
import com.waldorfprogramming.servicetools3.ui.theme.CAUTIONCOLOR
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
class EquipmentDetailsActivity: ComponentActivity() {

    private var customerId: String = ""
    private var equipmentId: String = ""
    private var customerFirstName: String = ""
    private var customerLastName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        if (bundle != null) {
            customerId = bundle.getString("CustomerId").toString()
            equipmentId = bundle.getString("EquipmentId").toString()
            customerFirstName = bundle.getString("customerFirstName").toString()
            customerLastName = bundle.getString("customerLastName").toString()
        }

        val viewModel: EquipmentDetailsViewModel by viewModels()

        setContent {
            ServiceTools3Theme {
                EquipmentDetailsScreen(
                    customerId,
                    equipmentId,
                    customerFirstName,
                    customerLastName,
                    viewModel
                )
            }
        }
    }

    private fun createNewPartsQuote(
        customerFirstName: String,
        customerLastName: String,
        equipmentName: String,
        equipmentBrand: String,
        equipmentModel: String,
        equipmentSerial: String
    ): Intent {
        val partsEmailIntent: Intent
        if (customerFirstName == "") {
            partsEmailIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_EMAIL, arrayOf(
                        "cherylhitemp@gmail.com",
                        "servicehitemp@gmail.com",
                        "tomhitemp@gmail.com"
                    )
                )
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    "$customerLastName Parts Quote for $equipmentName"
                )
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Equipment Name: $equipmentName \n" +
                            "Equipment Brand: $equipmentBrand \n" +
                            "Equipment Model: $equipmentModel \n" +
                            "Equipment Serial: $equipmentSerial \n\n"
                )
            }
        } else {
            partsEmailIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_EMAIL, arrayOf(
                        "cherylhitemp@gmail.com",
                        "servicehitemp@gmail.com",
                        "tomhitemp@gmail.com"
                    )
                )
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    "$customerFirstName $customerLastName Parts Quote for $equipmentName"
                )
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Equipment Name: $equipmentName \n" +
                            "Equipment Brand: $equipmentBrand \n" +
                            "Equipment Model: $equipmentModel \n" +
                            "Equipment Serial: $equipmentSerial \n\n"
                )
            }
        }
        return partsEmailIntent
    }

    private fun deleteCustomersEquipment(customerId: String, equipmentId: String) {
        val context = applicationContext
        val basicDb = FirebaseFirestore.getInstance()
        val customerDocumentRef = basicDb
            .collection("customers")
            .document(customerId)
            .collection("Equipment")
            .document(equipmentId)

        customerDocumentRef
            .delete()
            .addOnSuccessListener {
                val text: CharSequence = "Equipment Deleted!"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
                val returnToCustomerIntent =
                    Intent(this@EquipmentDetailsActivity, CustomerDetailsActivity::class.java)
                returnToCustomerIntent.putExtra("UpdateCustomerId", customerId)
                startActivity(returnToCustomerIntent)
                finish()
            }
            .addOnFailureListener {
                val text: CharSequence = "Failure: Something went wrong!"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
            }
    }


    @Composable
    fun EquipmentDetailsScreen(
        customerId: String,
        equipmentId: String,
        customerFirstName: String,
        customerLastName: String,
        viewModel: EquipmentDetailsViewModel
    ) {
        val equipmentDelete: () -> Unit = {
            deleteCustomersEquipment(
                customerId,
                equipmentId
            )
        }

        val openDeleteConfirmationDialog = remember { mutableStateOf(false)}

        Scaffold(
            topBar = {
                ServiceToolsTopBar(title = "Equipment Details")
            },
            floatingActionButton = {
                FABEditContent(contentDescription = "Edit Equipment Details") {
                val updateEquipmentIntent =
                    Intent(this@EquipmentDetailsActivity, EquipmentEditDetails::class.java)
                updateEquipmentIntent.putExtra("CustomerId", customerId)
                updateEquipmentIntent.putExtra("EquipmentId", equipmentId)
                startActivity(updateEquipmentIntent)
                }
            }
        ) {
            val context = LocalContext.current
            val scrollState = rememberScrollState()

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 10.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //Equipment Name
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = viewModel.equipmentState.value.equipmentName,
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onSurface
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {

                        //Equipment Brand
                        Column(
                            modifier = Modifier
                                .weight(.5f)
                                .padding(bottom = 4.dp)
                        ) {
                            if (viewModel.getEquipmentImageLogo(viewModel.equipmentState.value.equipmentBrand) == 3131) {
                                //No Brand Image Loaded
                                DetailLabel(label = "Brand Name")
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.Top,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        modifier = Modifier.padding(4.dp),
                                        text = viewModel.equipmentState.value.equipmentBrand,
                                        color = MaterialTheme.colors.onSurface,
                                        style = MaterialTheme.typography.h6,
                                    )
                                }

                            } else {
                                //Equipment Brand Image
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp)
                                        .padding(2.dp),
                                    elevation = 4.dp,
                                    shape = RoundedCornerShape(8.dp),
                                    backgroundColor = Color.White
                                ) {
                                    Image(
                                        painterResource(
                                            viewModel.getEquipmentImageLogo(
                                                viewModel.equipmentState.value.equipmentBrand
                                            )
                                        ),
                                        contentDescription = "Furnace Logo",
                                        contentScale = ContentScale.Fit,
                                    )
                                }
                            }
                        }
                        Column(
                            modifier = Modifier
                                .weight(.5f)
                        ) {

                            //Warranty Card
                            if (viewModel.equipmentState.value.laborWarranty.isEmpty()
                                && viewModel.equipmentState.value.equipmentWarranty.isNotEmpty()
                            ) {
                                DetailCard(
                                    labelText = "Warranty",
                                    dataText = "Parts: ${viewModel.equipmentState.value.equipmentWarranty}" +
                                            "\nLabor: No Labor Covered",
                                    data = false
                                )
                            } else if (
                                viewModel.equipmentState.value.equipmentWarranty.isNotEmpty()
                                && viewModel.equipmentState.value.laborWarranty.isNotEmpty()
                            ) {
                                DetailCard(
                                    labelText = "Warranty",
                                    dataText = "Parts: ${viewModel.equipmentState.value.equipmentWarranty}" +
                                            "\nLabor: ${viewModel.equipmentState.value.laborWarranty}",
                                    data = false
                                )
                            } else {
                                DetailCard(
                                    labelText = "Warranty",
                                    dataText = "Parts: No Parts Covered" +
                                            "\nLabor: No Labor Covered",
                                    data = false
                                )
                            }

                            //Maintenance Card
                            if (viewModel.equipmentState.value.equipmentContract.isEmpty()) {
                                DetailCard(
                                    labelText = "Maintenance",
                                    dataText = "None Entered",
                                    data = false
                                )
                            } else {
                                DetailCard(
                                    labelText = "Maintenance",
                                    dataText = "Exp: ${viewModel.equipmentState.value.equipmentContract}",
                                    data = false
                                )
                            }
                        }
                    }

                    //Model Label
                    DetailLabel(label = "Model")

                    //Model Text
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = viewModel.equipmentState.value.equipmentModel,
                            color = MaterialTheme.colors.onSurface,
                            style = MaterialTheme.typography.h5,
                        )
                    }

                    //Serial Label
                    DetailLabel(label = "Serial")

                    //Serial Text
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = viewModel.equipmentState.value.equipmentSerial,
                            color = MaterialTheme.colors.onSurface,
                            style = MaterialTheme.typography.h5,
                        )
                    }

                    //Data Label
                    DetailLabel(label = "Data")

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .weight(.5f)
                        ) {
                            //Fuel/Freon Card
                            DetailCard(
                                labelText = "Fuel / Freon",
                                dataText = viewModel.equipmentState.value.equipmentFuel,
                                data = true
                            )
                            //Efficiency Card
                            DetailCard(
                                labelText = "Efficiency",
                                dataText = viewModel.equipmentState.value.equipmentEff,
                                data = true
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(.5f)
                        ) {
                            //Voltage Card
                            DetailCard(
                                labelText = "Voltage",
                                dataText = viewModel.equipmentState.value.equipmentVoltage,
                                data = true
                            )
                            //Btu's Card
                            DetailCard(
                                labelText = "BTU's",
                                dataText = viewModel.equipmentState.value.equipmentBtu,
                                data = true
                            )
                        }
                    }

                    //Notes Label
                    DetailLabel(label = "Notes", clickable = true)

                    //Notes Card
                    Column(modifier = Modifier.fillMaxWidth()) {
                        //val notesScrollState = rememberScrollState()
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp)
                                .clickable {
                                val editEquipmentNotesIntent =
                                    Intent(context, EquipmentEditNotes::class.java)
                                editEquipmentNotesIntent.putExtra("customerId", customerId)
                                editEquipmentNotesIntent.putExtra("equipmentId", equipmentId)
                                editEquipmentNotesIntent
                                    .putExtra(
                                        "equipmentNotes",
                                        viewModel.equipmentState.value.equipmentNotes
                                    )
                                startActivity(editEquipmentNotesIntent)
                                },
                            elevation = 4.dp,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            if (viewModel.equipmentState.value.equipmentNotes.isEmpty()) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = "No notes added...",
                                    style = MaterialTheme.typography.body1,
                                )
                            } else {
                                Column {
                                    Text(
                                        modifier = Modifier.padding(8.dp),
                                        text = viewModel.equipmentState.value.equipmentNotes,
                                        style = MaterialTheme.typography.body1
                                    )
                                }
                            }
                        }
                    }

                    //Start a parts quote Button
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 16.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004D40)),
                        onClick = {
                            startActivity(
                                createNewPartsQuote(
                                    customerFirstName,
                                    customerLastName,
                                    viewModel.equipmentState.value.equipmentName,
                                    viewModel.equipmentState.value.equipmentBrand,
                                    viewModel.equipmentState.value.equipmentModel,
                                    viewModel.equipmentState.value.equipmentSerial
                                )
                            )
                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Start a Parts Quote",
                            color = Color.White,
                            style = MaterialTheme.typography.button,
                            fontSize = 18.sp
                        )
                    }

                    // Maintenance Manager Button
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 16.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004D40)),
                        onClick = {
//                        val maintenanceManagerIntent = Intent(this@EquipmentDetails, ContractView::class.java)
//                        maintenanceManagerIntent.putExtra("CustomerId", customerId)
//                        maintenanceManagerIntent.putExtra("EquipmentId", equipmentId)
//                        startActivity(maintenanceManagerIntent)
                        },
                        enabled = false
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Maintenance Manager",
                            color = Color.White,
                            style = MaterialTheme.typography.button,
                            fontSize = 18.sp
                        )
                    }

                    //Warranty manager
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 16.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004D40)),
                        onClick = {
//                        val equipmentWarrantyIntent = Intent(this@EquipmentDetails, WarrantyActivity::class.java)
//                        equipmentWarrantyIntent.putExtra("CustomerId", customerId)
//                        equipmentWarrantyIntent.putExtra("EquipmentId", equipmentId)
//                        startActivity(equipmentWarrantyIntent)
                        },
                        enabled = false
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Warranty Manager",
                            color = Color.White,
                            style = MaterialTheme.typography.button,
                            fontSize = 18.sp
                        )
                    }

                    //Delete the equipment Button
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 88.dp, horizontal = 16.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = CAUTIONCOLOR),
                        onClick = {
                            openDeleteConfirmationDialog.value = true
                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Delete This Equipment",
                            color = Color.White,
                            style = MaterialTheme.typography.button,
                            fontSize = 18.sp
                        )
                    }
                }
            }
            if(openDeleteConfirmationDialog.value){
                DeleteConfirmationDialog(
                    openDeleteConfirmationDialog,
                    equipmentDelete
                )
            }
        }
    }
}


