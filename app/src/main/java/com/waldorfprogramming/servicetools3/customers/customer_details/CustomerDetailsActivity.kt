package com.waldorfprogramming.servicetools3.customers.customer_details

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.ACTION_DIAL
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.firestore.FirebaseFirestore
import com.waldorfprogramming.servicetools3.components.DeleteConfirmationDialog
import com.waldorfprogramming.servicetools3.components.DetailLabel
import com.waldorfprogramming.servicetools3.components.FABEditContent
import com.waldorfprogramming.servicetools3.components.ServiceToolsTopBar
import com.waldorfprogramming.servicetools3.customers.customer_details.customer_billing_details.CustomerBillingDetails
import com.waldorfprogramming.servicetools3.customers.customer_list.CustomerListActivity
import com.waldorfprogramming.servicetools3.customers.equipment.equipment_list.EquipmentListActivity
import com.waldorfprogramming.servicetools3.customers.job_history.job_history_list.CustomerJobHistoryListActivity
import com.waldorfprogramming.servicetools3.daily_service.dispatch_create.DispatchCreate
import com.waldorfprogramming.servicetools3.ui.theme.CAUTIONCOLOR
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme
import com.waldorfprogramming.servicetools3.utilities.addressStringBuilder
import com.waldorfprogramming.servicetools3.utilities.removeNonNumbersFromString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
class CustomerDetailsActivity: ComponentActivity() {

    private var id: String = ""

    private var firestoreDB: FirebaseFirestore? = null
    private var logDate: String = ""
    private var userName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getString("UpdateCustomerId").toString()
        }

        val viewModel: CustomerDetailsViewModel by viewModels()

        firestoreDB = FirebaseFirestore.getInstance()
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("h:mm a", Locale.US)
        logDate = sdf.format(cal.time)

        val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)
        if (googleSignInAccount != null) {
            userName = googleSignInAccount.displayName.toString()
        }

        val customerDelete: () -> Unit = {
            deleteCustomer(id)
        }

        setContent {
            ServiceTools3Theme {

                val context = LocalContext.current

                val secondAddress = addressStringBuilder(
                    viewModel.customerState.value.city,
                    viewModel.customerState.value.state,
                    viewModel.customerState.value.zip
                )
                
                val openDeleteConfirmationDialog = remember { mutableStateOf(false)}

                Scaffold (
                    topBar = {
                        ServiceToolsTopBar(title = "Customer Details")
                    },
                    floatingActionButton = {
                        FABEditContent(contentDescription = "Edit Customer Details") {
                            val updateCustomerIntent =
                            Intent(this@CustomerDetailsActivity, CustomerEditDetails::class.java)
                            updateCustomerIntent.putExtra("UpdateCustomerId", id)
                            startActivity(updateCustomerIntent)
                        }
                    }
                ){
                    val scrollState = rememberScrollState()
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        Column (
                            modifier = Modifier
                                .padding(top = 40.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            if(viewModel.customerState.value.noService){
                                Card (
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    backgroundColor = Color.Red,
                                    elevation = 5.dp
                                ){
                                    Column {
                                        Text(
                                            modifier = Modifier.padding(8.dp),
                                            text = "No Service",
                                            fontStyle = FontStyle.Italic,
                                            style = MaterialTheme.typography.caption,
                                            color = Color.White
                                        )
                                        if(viewModel.customerState.value.firstname.isNotEmpty()){
                                            Row(modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 24.dp, vertical = 8.dp)
                                            ) {
                                                Text(
                                                    text = "${viewModel.customerState.value.firstname} ",
                                                    style = MaterialTheme.typography.h5,
                                                    color = Color.White
                                                )
                                                Text(
                                                    text = viewModel.customerState.value.lastname,
                                                    style = MaterialTheme.typography.h5,
                                                    color = Color.White
                                                )
                                            }

                                        } else {
                                            Row(modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 24.dp, vertical = 8.dp)
                                            ) {
                                                Text(
                                                    text = viewModel.customerState.value.lastname,
                                                    style = MaterialTheme.typography.h5,
                                                    color = Color.White
                                                )
                                            }
                                        }
                                        Text(
                                            modifier = Modifier.padding(8.dp),
                                            text = "Get office approval prior to any work",
                                            fontStyle = FontStyle.Italic,
                                            style = MaterialTheme.typography.caption,
                                            color = Color.White
                                        )
                                    }
                                }
                            } else {
                                if(viewModel.customerState.value.firstname.isNotEmpty()){
                                    Row(modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp, vertical = 8.dp)
                                    ) {
                                        Text(
                                            text = "${viewModel.customerState.value.firstname} ",
                                            style = MaterialTheme.typography.h5,
                                            color = MaterialTheme.colors.onSurface
                                        )
                                        Text(
                                            text = viewModel.customerState.value.lastname,
                                            style = MaterialTheme.typography.h5,
                                            color = MaterialTheme.colors.onSurface
                                        )
                                    }

                                } else {
                                    Row(modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp, vertical = 8.dp)
                                    ) {
                                        Text(
                                            text = viewModel.customerState.value.lastname,
                                            style = MaterialTheme.typography.h5,
                                            color = MaterialTheme.colors.onSurface
                                        )
                                    }
                                }
                            }

                            //Address Card
                            if(
                                viewModel.customerState.value.street.isNotEmpty() ||
                                viewModel.customerState.value.city.isNotEmpty() ||
                                viewModel.customerState.value.state.isNotEmpty() ||
                                viewModel.customerState.value.zip.isNotEmpty()
                            ){
                                DetailLabel(label = "Job Address", clickable = true)
                                Card(
                                    shape = RoundedCornerShape(8.dp),
                                    elevation = 4.dp,
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .clickable {
                                            val location = Uri.parse(
                                                "geo:0,0?q=${
                                                    viewModel.customerState.value.street
                                                }+${
                                                    viewModel.customerState.value.city
                                                }+${
                                                    viewModel.customerState.value.state
                                                }+${
                                                    viewModel.customerState.value.zip
                                                }"
                                            )
                                            val mapIntent = Intent(ACTION_VIEW, location)
                                            try {
                                                context.startActivity(mapIntent)
                                            } catch (e: ActivityNotFoundException) {
                                                Log.d("MAP_INTENT", "Map Error: ")
                                            }
                                        }

                                ){
                                    Column(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .background(color = MaterialTheme.colors.background)
                                    ) {
                                        if(viewModel.customerState.value.street.isNotEmpty()){
                                            Row(modifier = Modifier.fillMaxWidth()) {
                                                Text(
                                                    text = viewModel.customerState.value.street,
                                                    style = MaterialTheme.typography.h6,
                                                    color = MaterialTheme.colors.onSurface
                                                )
                                            }
                                        }
                                        if(secondAddress.isNotEmpty()){
                                            Row(modifier = Modifier.fillMaxWidth()) {
                                                Text(
                                                    text = secondAddress,
                                                    style = MaterialTheme.typography.h6,
                                                    color = MaterialTheme.colors.onSurface
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            //Primary Phone Card
                            if(viewModel.customerState.value.phoneName.isNotEmpty() ||
                                viewModel.customerState.value.phone.isNotEmpty()) {
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
                                                            viewModel.customerState.value.phone
                                                        )
                                                    )
                                                val phoneIntent = Intent(ACTION_DIAL, phoneNumber)
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
                                        if(viewModel.customerState.value.phoneName.isNotEmpty()){
                                            Row(modifier = Modifier.fillMaxWidth()) {
                                                Text(
                                                    text = viewModel.customerState.value.phoneName,
                                                    style = MaterialTheme.typography.h6,
                                                    color = MaterialTheme.colors.onSurface
                                                )
                                            }
                                        }
                                        if(viewModel.customerState.value.phone.isNotEmpty()){
                                            Row(modifier = Modifier.fillMaxWidth()) {
                                                Text(
                                                    text = viewModel.customerState.value.phone,
                                                    style = MaterialTheme.typography.h6,
                                                    color = MaterialTheme.colors.onSurface
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            //Alternate Phone
                            if(viewModel.customerState.value.altPhoneName.isNotEmpty() ||
                                viewModel.customerState.value.altphone.isNotEmpty()){
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
                                                        viewModel.customerState.value.altphone
                                                    )
                                                )
                                            val phoneIntent = Intent(ACTION_DIAL, phoneNumber)
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
                                        if(viewModel.customerState.value.altPhoneName.isNotEmpty()){
                                            Row(modifier = Modifier.fillMaxWidth()) {
                                                Text(
                                                    text = viewModel.customerState.value.altPhoneName,
                                                    style = MaterialTheme.typography.h6,
                                                    color = MaterialTheme.colors.onSurface
                                                )
                                            }
                                        }
                                        if(viewModel.customerState.value.altphone.isNotEmpty()){
                                            Row(modifier = Modifier.fillMaxWidth()) {
                                                Text(
                                                    text = viewModel.customerState.value.altphone,
                                                    style = MaterialTheme.typography.h6,
                                                    color = MaterialTheme.colors.onSurface
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            //Other Phone
                            if(viewModel.customerState.value.otherPhoneName.isNotEmpty() ||
                                viewModel.customerState.value.otherPhone.isNotEmpty()){
                                    DetailLabel(label = "Other Contact", clickable = true)
                                Card(
                                    shape = RoundedCornerShape(8.dp),
                                    elevation = 4.dp,
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .clickable {
                                            val phoneNumber =
                                                Uri.parse(
                                                    "tel:" + removeNonNumbersFromString(
                                                        viewModel.customerState.value.otherPhone
                                                    )
                                                )
                                            val phoneIntent = Intent(ACTION_DIAL, phoneNumber)
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
                                        if(viewModel.customerState.value.otherPhoneName.isNotEmpty()){
                                            Row(modifier = Modifier.fillMaxWidth()) {
                                                Text(
                                                    text = viewModel.customerState.value.otherPhoneName,
                                                    style = MaterialTheme.typography.h6,
                                                    color = MaterialTheme.colors.onSurface
                                                )
                                            }
                                        }
                                        if(viewModel.customerState.value.otherPhone.isNotEmpty()){
                                            Row(modifier = Modifier.fillMaxWidth()) {
                                                Text(
                                                    text = viewModel.customerState.value.otherPhone,
                                                    style = MaterialTheme.typography.h6,
                                                    color = MaterialTheme.colors.onSurface
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            //Notes
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                DetailLabel(label = "Notes", clickable = true)
                                Card(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(2.dp)
                                    .border(2.dp, color = Color(0xFF004D40))
                                    .clickable {
                                        val editNotesIntent =
                                            Intent(
                                                this@CustomerDetailsActivity,
                                                CustomerEditNotes::class.java
                                            )
                                        editNotesIntent.putExtra("UpdateCustomerId", id)
                                        editNotesIntent.putExtra(
                                            "UpdateCustomerNotes",
                                            viewModel.customerState.value.cnotes
                                        )
                                        startActivity(editNotesIntent)
                                    },
                                    shape = RoundedCornerShape(5.dp),
                                    elevation = 4.dp
                                ) {
                                    if(viewModel.customerState.value.cnotes.isNullOrEmpty()) {
                                        Text(
                                            modifier = Modifier.padding(4.dp),
                                            text = "No Notes..."
                                        )
                                    } else {
                                        Text(
                                            modifier = Modifier.padding(4.dp),
                                            text = viewModel.customerState.value.cnotes
                                        )
                                    }
                                }
                            }

                            //Equipment Button
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 48.dp, start = 16.dp, end = 16.dp),
                                shape = RoundedCornerShape(5.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004D40)),
                                onClick = {
                                    val equipmentIntent = Intent(this@CustomerDetailsActivity, EquipmentListActivity::class.java)
                                    equipmentIntent.putExtra("customerId", id)
                                    equipmentIntent.putExtra("customerFirstName", viewModel.customerState.value.firstname)
                                    equipmentIntent.putExtra("customerLastName", viewModel.customerState.value.lastname)
                                    startActivity(equipmentIntent)
                                }
                            ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = "Equipment",
                                    color = Color.White,
                                    style = MaterialTheme.typography.button,
                                    fontSize = 18.sp
                                )
                            }

                            //Billing Information Button
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                shape = RoundedCornerShape(5.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004D40)),
                                onClick = {
                                    val billingIntent = Intent(this@CustomerDetailsActivity, CustomerBillingDetails::class.java)
                                    billingIntent.putExtra("UpdateCustomerId", id)
                                    startActivity(billingIntent)
                                }
                            ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = "Billing Information",
                                    color = Color.White,
                                    style = MaterialTheme.typography.button,
                                    fontSize = 18.sp
                                )
                            }

                            // New Dispatch Button
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                shape = RoundedCornerShape(5.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004D40)),
                                onClick = {
                                    val createDispatchIntent = Intent(this@CustomerDetailsActivity, DispatchCreate::class.java)
                                    createDispatchIntent.putExtra("UpdateCustomerAltPhoneName", viewModel.customerState.value.altPhoneName)
                                    createDispatchIntent.putExtra("UpdateCustomerAltPhone", viewModel.customerState.value.altphone)
                                    createDispatchIntent.putExtra("UpdateCustomerCity", secondAddress)
                                    createDispatchIntent.putExtra("UpdateCustomerId", id)
                                    createDispatchIntent.putExtra("UpdateCustomerFirstName", viewModel.customerState.value.firstname)
                                    createDispatchIntent.putExtra("UpdateCustomerLastName", viewModel.customerState.value.lastname)
                                    createDispatchIntent.putExtra("UpdateCustomerPhone", viewModel.customerState.value.phone)
                                    createDispatchIntent.putExtra("UpdateCustomerPhoneName", viewModel.customerState.value.phoneName)
                                    createDispatchIntent.putExtra("UpdateCustomerStreet", viewModel.customerState.value.street)
                                    startActivity(createDispatchIntent)
                                }
                            ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = "Start A New Dispatch",
                                    color = Color.White,
                                    style = MaterialTheme.typography.button,
                                    fontSize = 18.sp
                                )
                            }

                            // Job History Button
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                shape = RoundedCornerShape(5.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004D40)),
                                onClick = {
                                    Log.d("firebase", "onCreate: $id")
                                    val customerJobHistoryIntent = Intent(this@CustomerDetailsActivity, CustomerJobHistoryListActivity::class.java)
                                    customerJobHistoryIntent.putExtra("CustomerId", id)
                                    startActivity(customerJobHistoryIntent)
                                }
                            ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = "Job History",
                                    color = Color.White,
                                    style = MaterialTheme.typography.button,
                                    fontSize = 18.sp
                                )
                            }

                            //Delete the customer Button
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
                                    text = "Delete This Customer",
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
                            customerDelete
                        )
                    }
                }
            }
        }
    }

    private fun deleteCustomer(id: String) {
        val context = applicationContext
        val customerDocumentRef = firestoreDB?.collection("customers")?.document(id)
        customerDocumentRef
            ?.delete()
            ?.addOnSuccessListener {
                val text: CharSequence = "Customer Deleted!"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                val deleteIntent = Intent(this, CustomerListActivity::class.java)
                startActivity(deleteIntent)
                toast.show()
                finish()
            }
            ?.addOnFailureListener {
                val text: CharSequence = "Failure: Something went wrong!"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
            }
    }
}