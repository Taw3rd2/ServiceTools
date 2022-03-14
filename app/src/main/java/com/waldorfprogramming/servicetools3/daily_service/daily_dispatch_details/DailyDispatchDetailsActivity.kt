package com.waldorfprogramming.servicetools3.daily_service.daily_dispatch_details

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.waldorfprogramming.servicetools3.components.DetailCard
import com.waldorfprogramming.servicetools3.components.DetailLabel
import com.waldorfprogramming.servicetools3.components.FABEditContent
import com.waldorfprogramming.servicetools3.components.ServiceToolsTopBar
import com.waldorfprogramming.servicetools3.customers.customer_details.CustomerEditNotes
import com.waldorfprogramming.servicetools3.customers.equipment.equipment_list.EquipmentListActivity
import com.waldorfprogramming.servicetools3.daily_service.daily_dispatch_details.time_keeper.TimeKeeperActivity
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme
import com.waldorfprogramming.servicetools3.utilities.javaDateToString
import com.waldorfprogramming.servicetools3.utilities.removeNonNumbersFromString

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
class DailyDispatchDetailsActivity: ComponentActivity() {

    private var dispatchId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        if (bundle != null) {
            dispatchId = bundle.getString("DispatchId").toString()
        }

        val viewModel: DailyDispatchDetailsViewModel by viewModels()

        setContent {
            ServiceTools3Theme {

                val context = LocalContext.current

                Scaffold(
                    topBar = {
                        ServiceToolsTopBar(title = "Daily Service")
                    },
                    floatingActionButton = {
                        FABEditContent(contentDescription = "Edit Dispatch Details") {
                            //TODO Intent to dispatch editor
                        }
                    }
                ) {
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
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = viewModel.dispatchState.value.issue,
                                style = MaterialTheme.typography.h4,
                                color = MaterialTheme.colors.onSurface
                            )
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = viewModel.dispatchState.value.jobNumber,
                                style = MaterialTheme.typography.h5,
                                color = MaterialTheme.colors.onSurface
                            )
                            
                            Spacer(modifier = Modifier.padding(top = 2.dp))

                            //Name
                            DetailLabel(label = "Job Status")
                            Row {
                                Column(
                                    modifier = Modifier
                                        .weight(.5f)
                                        .wrapContentWidth(Alignment.Start)
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = viewModel.dispatchState.value.status,
                                        style = MaterialTheme.typography.h6
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(.5f)
                                        .wrapContentWidth(Alignment.End)
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = javaDateToString(viewModel.dispatchState.value.end),
                                        style = MaterialTheme.typography.h6
                                    )
                                }
                            }

                            DetailLabel(label = "Customer Name")
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                elevation = 4.dp,
                                modifier = Modifier
                                    .padding(2.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .background(color = MaterialTheme.colors.background)
                                ) {
                                    if(viewModel.dispatchState.value.firstname.isNotEmpty()){
                                        Row(modifier = Modifier.fillMaxWidth()) {
                                            Text(
                                                text = viewModel.dispatchState.value.firstname,
                                                style = MaterialTheme.typography.h6,
                                                color = MaterialTheme.colors.onSurface
                                            )
                                            Text(
                                                text = " ${viewModel.dispatchState.value.lastname}",
                                                style = MaterialTheme.typography.h6,
                                                color = MaterialTheme.colors.onSurface
                                            )
                                        }
                                    } else {
                                        Row(modifier = Modifier.fillMaxWidth()) {
                                            Text(
                                                text = viewModel.dispatchState.value.lastname,
                                                style = MaterialTheme.typography.h6,
                                                color = MaterialTheme.colors.onSurface
                                            )
                                        }
                                    }
                                }
                            }

                            //Job Address
                            DetailLabel(label = "Job Address", true)
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                elevation = 4.dp,
                                modifier = Modifier
                                    .padding(2.dp)
                                    .clickable {
                                        val location = Uri.parse(
                                            "geo:0,0?q=${
                                                viewModel.dispatchState.value.street
                                            }+${
                                                viewModel.dispatchState.value.city
                                            }"
                                        )
                                        val mapIntent = Intent(Intent.ACTION_VIEW, location)
                                        try {
                                            context.startActivity(mapIntent)
                                        } catch (e: ActivityNotFoundException) {
                                            Log.d("MAP_INTENT", "Map Error: ")
                                        }
                                    }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .background(color = MaterialTheme.colors.background)
                                ) {
                                    if(viewModel.dispatchState.value.street.isNotEmpty()){
                                        Row(modifier = Modifier.fillMaxWidth()) {
                                            Text(
                                                text = viewModel.dispatchState.value.street,
                                                style = MaterialTheme.typography.h6,
                                                color = MaterialTheme.colors.onSurface
                                            )
                                        }
                                    }
                                    if(viewModel.dispatchState.value.city.isNotEmpty()){
                                        Row(modifier = Modifier.fillMaxWidth()) {
                                            Text(
                                                text = viewModel.dispatchState.value.city,
                                                style = MaterialTheme.typography.h6,
                                                color = MaterialTheme.colors.onSurface
                                            )
                                        }
                                    }
                                }
                            }

                            //Primary Contact
                            if(viewModel.dispatchState.value.phoneName.isNotEmpty() ||
                                viewModel.dispatchState.value.phone.isNotEmpty()) {
                                DetailLabel(label = "Primary Contact", clickable = true)
                                Card(
                                    shape = RoundedCornerShape(8.dp),
                                    elevation = 4.dp,
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .clickable {
                                            val phoneNumber =
                                                Uri.parse(
                                                    "tel:" + removeNonNumbersFromString(
                                                        viewModel.dispatchState.value.phone
                                                    )
                                                )
                                            val phoneIntent =
                                                Intent(Intent.ACTION_DIAL, phoneNumber)
                                            try {
                                                context.startActivity(phoneIntent)
                                            } catch (e: ActivityNotFoundException) {
                                                Log.d(
                                                    "PHONE_INTENT",
                                                    "Phone Dial Error: ${e.message}"
                                                )
                                            }
                                        }
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .background(color = MaterialTheme.colors.background)
                                    ) {
                                        if(viewModel.dispatchState.value.phoneName.isNotEmpty()){
                                            Row(modifier = Modifier.fillMaxWidth()) {
                                                Text(
                                                    text = viewModel.dispatchState.value.phoneName,
                                                    style = MaterialTheme.typography.h6,
                                                    color = MaterialTheme.colors.onSurface
                                                )
                                            }
                                        }
                                        if(viewModel.dispatchState.value.phone.isNotEmpty()){
                                            Row(modifier = Modifier.fillMaxWidth()) {
                                                Text(
                                                    text = viewModel.dispatchState.value.phone,
                                                    style = MaterialTheme.typography.h6,
                                                    color = MaterialTheme.colors.onSurface
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            //Alternate Contact
                            if(viewModel.dispatchState.value.altPhoneName.isNotEmpty() ||
                                viewModel.dispatchState.value.altphone.isNotEmpty()){
                                DetailLabel(label = "Alternate Contact", clickable = true)
                                Card(
                                    shape = RoundedCornerShape(8.dp),
                                    elevation = 4.dp,
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .clickable {
                                            val phoneNumber =
                                                Uri.parse(
                                                    "tel:" + removeNonNumbersFromString(
                                                        viewModel.dispatchState.value.altphone
                                                    )
                                                )
                                            val phoneIntent =
                                                Intent(Intent.ACTION_DIAL, phoneNumber)
                                            try {
                                                context.startActivity(phoneIntent)
                                            } catch (e: ActivityNotFoundException) {
                                                Log.d(
                                                    "PHONE_INTENT",
                                                    "Phone Dial Error: ${e.message}"
                                                )
                                            }
                                        }
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .background(color = MaterialTheme.colors.background)
                                    ) {
                                        if(viewModel.dispatchState.value.altPhoneName.isNotEmpty()){
                                            Row(modifier = Modifier.fillMaxWidth()) {
                                                Text(
                                                    text = viewModel.dispatchState.value.altPhoneName,
                                                    style = MaterialTheme.typography.h6,
                                                    color = MaterialTheme.colors.onSurface
                                                )
                                            }
                                        }
                                        if(viewModel.dispatchState.value.altphone.isNotEmpty()){
                                            Row(modifier = Modifier.fillMaxWidth()) {
                                                Text(
                                                    text = viewModel.dispatchState.value.altphone,
                                                    style = MaterialTheme.typography.h6,
                                                    color = MaterialTheme.colors.onSurface
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            //Job Notes
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                DetailLabel(label = "Job Notes")
                                Card(
                                    shape = RoundedCornerShape(8.dp),
                                    elevation = 4.dp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(2.dp)
                                ) {
                                    if(viewModel.dispatchState.value.notes.isNullOrEmpty()) {
                                        Text(
                                            modifier = Modifier.padding(4.dp),
                                            text = "No Notes..."
                                        )
                                    } else {
                                        Text(
                                            modifier = Modifier.padding(4.dp),
                                            text = viewModel.dispatchState.value.notes
                                        )
                                    }
                                }
                            }

                            //Data Label
                            DetailLabel(label = "Details")

                            Row(modifier = Modifier.fillMaxWidth()) {
                                Column(
                                    modifier = Modifier
                                        .weight(.5f)
                                ) {
                                    //Tech 1
                                    DetailCard(
                                        labelText = "Tech 1",
                                        dataText = viewModel.dispatchState.value.techLead,
                                        data = true
                                    )
                                    //Time Allotted
                                    DetailCard(
                                        labelText = "Slotted Time",
                                        dataText = viewModel.dispatchState.value.timeAlotted,
                                        data = true
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(.5f)
                                ) {
                                    //Voltage Card
                                    DetailCard(
                                        labelText = "Tech 2",
                                        dataText = viewModel.dispatchState.value.techHelper,
                                        data = true
                                    )
                                    //Btu's Card
                                    DetailCard(
                                        labelText = "Payment",
                                        dataText = viewModel.dispatchState.value.payment,
                                        data = true
                                    )
                                }
                            }

                            //Equipment Button
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 32.dp,
                                        bottom = 16.dp,
                                        start = 16.dp,
                                        end = 16.dp
                                    ),
                                shape = RoundedCornerShape(5.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004D40)),
                                onClick = {
                                    val equipmentIntent = Intent(this@DailyDispatchDetailsActivity, EquipmentListActivity::class.java)
                                    equipmentIntent.putExtra("customerId", viewModel.dispatchState.value.customerId)
                                    equipmentIntent.putExtra("customerFirstName", viewModel.dispatchState.value.firstname)
                                    equipmentIntent.putExtra("customerLastName", viewModel.dispatchState.value.lastname)
                                    startActivity(equipmentIntent)
                                }
                            ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = "Equipment List",
                                    color = Color.White,
                                    style = MaterialTheme.typography.button,
                                    fontSize = 18.sp
                                )
                            }

                            //Time Keeper Button
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp, horizontal = 16.dp),
                                shape = RoundedCornerShape(5.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004D40)),
                                onClick = {
                                    val timeKeeperIntent = Intent(this@DailyDispatchDetailsActivity, TimeKeeperActivity::class.java)
                                    timeKeeperIntent.putExtra("DispatchId", viewModel.dispatchState.value.dispatchId)
                                    startActivity(timeKeeperIntent)
                                }
                            ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = "Time Keeper",
                                    color = Color.White,
                                    style = MaterialTheme.typography.button,
                                    fontSize = 18.sp
                                )
                            }

                            //Time Keeper Button
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 16.dp,
                                        bottom = 88.dp,
                                        start = 16.dp,
                                        end = 16.dp
                                    ),
                                shape = RoundedCornerShape(5.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004D40)),
                                onClick = {
//                                    val timeKeeperIntent = Intent(this@DailyDispatchDetailsActivity, EquipmentListActivity::class.java)
//                                    timeKeeperIntent.putExtra("DispatchId", viewModel.dispatchState.value.dispatchId)
//                                    startActivity(timeKeeperIntent)
                                },
                                enabled = false
                            ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = "Create New Invoice",
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
}