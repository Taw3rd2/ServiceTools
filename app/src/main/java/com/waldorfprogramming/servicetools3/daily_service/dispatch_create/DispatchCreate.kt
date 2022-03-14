package com.waldorfprogramming.servicetools3.daily_service.dispatch_create

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.FirebaseFirestore
import com.waldorfprogramming.servicetools3.R
import com.waldorfprogramming.servicetools3.adapters.createWithFactory
import com.waldorfprogramming.servicetools3.components.DefaultDropDown
import com.waldorfprogramming.servicetools3.components.ServiceToolsTopBar
import com.waldorfprogramming.servicetools3.daily_service.dispatch_create.payment_drop_down.PaymentDropDown
import com.waldorfprogramming.servicetools3.daily_service.dispatch_create.payment_drop_down.PaymentDropDownRepository
import com.waldorfprogramming.servicetools3.daily_service.dispatch_create.payment_drop_down.PaymentDropDownViewModel
import com.waldorfprogramming.servicetools3.daily_service.dispatch_create.technician_drop_down.TechnicianDropDown
import com.waldorfprogramming.servicetools3.daily_service.dispatch_create.technician_drop_down.TechnicianDropDownRepository
import com.waldorfprogramming.servicetools3.daily_service.dispatch_create.technician_drop_down.TechnicianDropDownViewModel
import com.waldorfprogramming.servicetools3.daily_service.dispatch_create.work_list_drop_down.WorkListDropDown
import com.waldorfprogramming.servicetools3.daily_service.dispatch_create.work_list_drop_down.WorkListDropDownRepository
import com.waldorfprogramming.servicetools3.daily_service.dispatch_create.work_list_drop_down.WorkListDropDownViewModel
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
class DispatchCreate: AppCompatActivity() {

    private var altPhoneName: String = ""
    private var altphone: String = ""
    private var city: String = ""
    private var customerId: String = ""
    private var firstname: String = ""
    private var lastname: String = ""
    private var phone: String = ""
    private var phoneName: String = ""
    private var street: String = ""
    private var takenBy: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dispatch_create )

        val bundle = intent.extras
        if (bundle != null) {
            altPhoneName = bundle.getString("UpdateCustomerAltPhoneName").toString()
            altphone = bundle.getString("UpdateCustomerAltPhone").toString()
            city = bundle.getString("UpdateCustomerCity").toString()
            customerId = bundle.getString("UpdateCustomerId").toString()
            firstname = bundle.getString("UpdateCustomerFirstName").toString()
            lastname = bundle.getString("UpdateCustomerLastName").toString()
            phone = bundle.getString("UpdateCustomerPhone").toString()
            phoneName = bundle.getString("UpdateCustomerPhoneName").toString()
            street = bundle.getString("UpdateCustomerStreet").toString()
        }

        val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)
        if (googleSignInAccount != null) {
            takenBy = googleSignInAccount.displayName.toString()
        }

        val paymentViewModel: PaymentDropDownViewModel by lazy {
            ViewModelProvider(
                this,
                createWithFactory {
                    PaymentDropDownViewModel(
                        repository = PaymentDropDownRepository()
                    )
                }
            ).get(PaymentDropDownViewModel::class.java)
        }

        val technicianViewModel: TechnicianDropDownViewModel by lazy {
            ViewModelProvider(
                this,
                createWithFactory {
                    TechnicianDropDownViewModel(
                        repository = TechnicianDropDownRepository()
                    )
                }
            ).get(TechnicianDropDownViewModel::class.java)
        }

        val workListViewModel: WorkListDropDownViewModel by lazy {
            ViewModelProvider(
                this,
                createWithFactory {
                    WorkListDropDownViewModel(
                        repository = WorkListDropDownRepository()
                    )
                }
            ).get(WorkListDropDownViewModel::class.java)
        }

        val dispatchCreateComposeView = findViewById<ComposeView>(R.id.dispatch_create_compose_view)
        dispatchCreateComposeView.setContent {
            ServiceTools3Theme {

                //list of variables
                var editableAltPhoneName by remember { mutableStateOf("") }
                var editableAltPhone by remember { mutableStateOf("") }
                var editableCity by remember { mutableStateOf("") }
                var editableDateCreated by remember { mutableStateOf(Date()) }
                var editableDateModified by remember { mutableStateOf(Date()) }
                var editableDateScheduled by remember { mutableStateOf(Date()) }
                var editableEnd by remember { mutableStateOf(Date()) }
                var editableFirstName by remember { mutableStateOf("") }
                var editableIssue by remember { mutableStateOf("") }
                var editableJobNumber by remember { mutableStateOf("") }
                var editableLastName by remember { mutableStateOf("") }
                var editableLeadSource by remember { mutableStateOf("PC") }
                var editableNotes by remember { mutableStateOf("") }
                var editablePayment by remember { mutableStateOf("") }
                var editablePhone by remember { mutableStateOf("") }
                var editablePhoneName by remember { mutableStateOf("") }
                var editableScheduledDate by remember { mutableStateOf(0L) }
                var editableShorthand by remember { mutableStateOf("") }
                var editableStart by remember { mutableStateOf(Date()) }
                val editableStatus by remember { mutableStateOf("scheduled") }
                var editableStreet by remember { mutableStateOf("") }
                var editableTakenBy by remember { mutableStateOf("") }
                var editableTechHelper by remember { mutableStateOf("NONE") }
                var editableTechHelperId by remember { mutableStateOf("") }
                var editableTechLead by remember { mutableStateOf("") }
                var editableTechLeadId by remember{ mutableStateOf("") }
                var editableTimeAllotted by remember { mutableStateOf("1.5") }
                var editableTimeOfDay by remember { mutableStateOf("Anytime") }
                var editableTitle by remember { mutableStateOf("") }

                //Time of Day
                val timeOfDayList = listOf(
                    "AM", "Anytime", "First Call", "Last Call", "overtime", "PM"
                )

                var datePickerButtonText by remember { mutableStateOf("Set Date")}

                if (altphone.isNotEmpty()) {
                    editableAltPhone = altphone
                }

                if (altPhoneName.isNotEmpty()) {
                    editableAltPhoneName = altPhoneName
                }

                if (city.isNotEmpty()) {
                    editableCity = city
                }

                if (firstname.isNotEmpty()) {
                    editableFirstName = firstname
                }

                if (lastname.isNotEmpty()) {
                    editableLastName = lastname
                }

                if (phone.isNotEmpty()) {
                    editablePhone = phone
                }

                if (phoneName.isNotEmpty()) {
                    editablePhoneName = phoneName
                }

                if (street.isNotEmpty()) {
                    editableStreet = street
                }

                if(takenBy.isNotEmpty()) {
                    editableTakenBy = if(takenBy == "Thomas Waldorf"){
                        "Thomas"
                    } else {
                        takenBy
                    }
                }

                fun titleBuilder(
                    timeAllotted: String,
                    lastName: String,
                    shorthand: String,
                    timeOfDay: String
                ): String {
                    return "$timeAllotted/$lastName/$shorthand/$timeOfDay"
                }

                fun openDatePickerDialog(
                    setButtonText: (String) -> Unit
                ){
                    val scheduledDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select Service Date")
                        .build()
                    scheduledDatePicker.show(supportFragmentManager, "scheduledDatePicker")

                    scheduledDatePicker.addOnPositiveButtonClickListener {
                        val simpleDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)
                        simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")

                        val dateString = simpleDateFormat.format(Date(it))
                        setButtonText(dateString)
                        val calendar = Calendar.getInstance()

                        editableDateCreated = calendar.time
                        editableDateModified = calendar.time

                        //set to 12am
                        calendar.set(Calendar.HOUR_OF_DAY, 0)
                        calendar.set(Calendar.MINUTE, 0)
                        calendar.set(Calendar.SECOND, 0)
                        calendar.set(Calendar.MILLISECOND, 0)

                        editableDateScheduled = calendar.time
                        editableStart = calendar.time
                        editableScheduledDate = calendar.timeInMillis / 1000

                        //set hour to 1am
                        calendar.set(Calendar.HOUR_OF_DAY, 1)
                        editableEnd = calendar.time

                        Toast.makeText(this, "$dateString chosen", Toast.LENGTH_SHORT).show()
                    }
                    scheduledDatePicker.addOnNegativeButtonClickListener {
                        Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
                    }
                }

                var isCommercial by remember { mutableStateOf(false) }
                val scaffoldState = rememberScaffoldState()
                val scrollState = rememberScrollState()
                val context = LocalContext.current

                Scaffold(
                    topBar = {
                        ServiceToolsTopBar(title = "Create New Dispatch")
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
                        Row(modifier = Modifier.padding(top = 24.dp)) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.5f),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    shape = RoundedCornerShape(5.dp),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                                    onClick = {
                                        openDatePickerDialog { text ->
                                            datePickerButtonText = text
                                        }
                                    }
                                ) {
                                    Text(
                                        modifier = Modifier.padding(8.dp),
                                        text = datePickerButtonText,
                                        color = Color.White,
                                        style = MaterialTheme.typography.button,
                                        fontSize = 18.sp
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.5f),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (isCommercial) {
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
                                    checked = isCommercial,
                                    onCheckedChange = { isCommercial = it },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.Green,
                                        checkedTrackColor = Color.Green,
                                    )
                                )
                            }
                        }

                        //Business Name
                        if (isCommercial) {
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 16.dp, top = 16.dp, end = 16.dp
                                    ),
                                value = editableLastName,
                                onValueChange = { editableLastName = it },
                                label = { Text(text = "Business Name or Landlords Name") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next,
                                    capitalization = KeyboardCapitalization.Words
                                ),
                                singleLine = true
                            )
                        } else {
                            Row {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(0.5f),
                                    verticalArrangement = Arrangement.Top,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    //First Name
                                    OutlinedTextField(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 16.dp, top = 24.dp, end = 16.dp
                                            ),
                                        value = editableFirstName,
                                        onValueChange = { editableFirstName = it },
                                        label = { Text(text = "First Name") },
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Text,
                                            imeAction = ImeAction.Next,
                                            capitalization = KeyboardCapitalization.Words
                                        ),
                                        singleLine = true
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(0.5f),
                                    verticalArrangement = Arrangement.Top,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    //Last Name
                                    OutlinedTextField(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 16.dp, top = 24.dp, end = 16.dp
                                            ),
                                        value = editableLastName,
                                        onValueChange = { editableLastName = it },
                                        label = { Text(text = "Last Name") },
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Text,
                                            imeAction = ImeAction.Next,
                                            capitalization = KeyboardCapitalization.Words
                                        ),
                                        singleLine = true
                                    )
                                }
                            }
                        }

                        //Job Street Address
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableStreet,
                            onValueChange = { editableStreet = it },
                            label = { Text(text = "Street Address") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            singleLine = true
                        )

                        //City
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableCity,
                            onValueChange = { editableCity = it },
                            label = { Text(text = "City, State, Zip Code") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            singleLine = true
                        )

                        Row {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(.5f),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                //Primary Contact Name
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 16.dp, top = 16.dp, end = 16.dp
                                        ),
                                    value = editablePhoneName,
                                    onValueChange = { editablePhoneName = it },
                                    label = { Text(text = "Primary Contact") },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Next,
                                        capitalization = KeyboardCapitalization.Words
                                    ),
                                    singleLine = true
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(.5f),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                //Alternate Contact Name
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 16.dp, top = 16.dp, end = 16.dp
                                        ),
                                    value = editableAltPhoneName,
                                    onValueChange = { editableAltPhoneName = it },
                                    label = { Text(text = "Alternate Contact") },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Next,
                                        capitalization = KeyboardCapitalization.Words
                                    ),
                                    singleLine = true
                                )
                            }
                        }

                        Row {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(.5f),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                //Primary Contact Phone
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 16.dp, top = 16.dp, end = 16.dp
                                        ),
                                    value = editablePhone,
                                    onValueChange = { editablePhone = it },
                                    label = { Text(text = "Primary Phone Number") },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Phone,
                                        imeAction = ImeAction.Next
                                    ),
                                    singleLine = true
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(.5f),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                //Alternate Contact Phone
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 16.dp, top = 16.dp, end = 16.dp
                                        ),
                                    value = editableAltPhone,
                                    onValueChange = { editableAltPhone = it },
                                    label = { Text(text = "Alternate Phone Number") },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Phone,
                                        imeAction = ImeAction.Next
                                    ),
                                    singleLine = true
                                )
                            }
                        }

                        Spacer(modifier = Modifier.padding(top = 16.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start
                        ) {
                            WorkListDropDown(
                                viewModel = workListViewModel,
                                setEditable = { work ->
                                    editableIssue = work
                                },
                                setEditableShorthand = { work ->
                                    editableShorthand = work
                                },
                                selectedEditable = editableIssue,
                                label = "Issue"
                            )
                        }

                        Row {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(.5f),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                //Slotted Time
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 16.dp, top = 16.dp, end = 16.dp
                                        ),
                                    value = editableTimeAllotted,
                                    onValueChange = { editableTimeAllotted = it },
                                    label = { Text(text = "Allotted Time") },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Next
                                    ),
                                    singleLine = true
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(.5f),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                //Lead Source
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 16.dp, top = 16.dp, end = 16.dp
                                        ),
                                    value = editableLeadSource,
                                    onValueChange = { editableLeadSource = it },
                                    label = { Text(text = "Lead Source") },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Next
                                    ),
                                    singleLine = true
                                )
                            }
                        }

                        //Tech Drop Downs
                        Spacer(modifier = Modifier.padding(top = 16.dp))
                        Row {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(.5f),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TechnicianDropDown(
                                    viewModel = technicianViewModel,
                                    setEditable = { lead ->
                                        editableTechLead = lead
                                    },
                                    setEditableId = { id ->
                                        editableTechLeadId = id
                                    },
                                    selectedEditable = editableTechLead,
                                    label = "Tech Lead",
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(.5f),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TechnicianDropDown(
                                    viewModel = technicianViewModel,
                                    setEditable = { helper ->
                                        editableTechHelper = helper
                                    },
                                    setEditableId = { id ->
                                        editableTechHelperId = id
                                    },
                                    selectedEditable = editableTechHelper,
                                    label = "Tech Helper",
                                )
                            }
                        }

                        //Payment and Time Of Day Drop Downs
                        Spacer(modifier = Modifier.padding(top = 16.dp))
                        Row {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(.5f),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                PaymentDropDown(
                                    viewModel = paymentViewModel,
                                    setEditable = { payment ->
                                        editablePayment = payment
                                    },
                                    selectedEditable = editablePayment,
                                    label = "Payment"
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(.5f),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                DefaultDropDown(
                                    listOfItems = timeOfDayList,
                                    setEditable = { time ->
                                        editableTimeOfDay = time
                                    },
                                    selectedEditable = editableTimeOfDay,
                                    label = "Time Of Day"
                                )
                            }
                        }

                        //Job Number
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableJobNumber,
                            onValueChange = { editableJobNumber = it },
                            label = { Text(text = "Job Number") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            singleLine = true
                        )

                        //Notes
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp, top = 16.dp, end = 16.dp
                                ),
                            value = editableNotes,
                            onValueChange = { editableNotes = it },
                            label = { Text(text = "Job Notes") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text
                            )
                        )

                        //Update Button
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = 16.dp,
                                    bottom = 16.dp,
                                    start = 16.dp,
                                    end = 16.dp
                                ),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                            onClick = {
                                editableTitle = titleBuilder(
                                    editableTimeAllotted,
                                    editableLastName,
                                    editableShorthand,
                                    editableTimeOfDay
                                )

                                val db = FirebaseFirestore.getInstance()

                                val documentForId = db.collection("events").document()
                                val techLeadGeneratedId = documentForId.id

                                if(editableTechHelper != "NONE"){

                                    val documentForHelperId = db.collection("events").document()
                                    val techHelperGeneratedId = documentForHelperId.id

                                    //First Service tech To Add
                                    val docData = hashMapOf(
                                        "id" to techLeadGeneratedId,
                                        "altPhoneName" to editableAltPhoneName,
                                        "altphone" to editableAltPhone,
                                        "city" to editableCity,
                                        "customerId" to customerId,
                                        "dateCreated" to editableDateCreated,
                                        "dateModified" to editableDateModified,
                                        "dateScheduled" to editableDateScheduled,
                                        "end" to editableEnd,
                                        "firstname" to editableFirstName,
                                        "issue" to editableIssue,
                                        "jobNumber" to editableJobNumber,
                                        "lastname" to editableLastName,
                                        "leadSource" to editableLeadSource,
                                        "notes" to editableNotes,
                                        "payment" to editablePayment,
                                        "phone" to editablePhone,
                                        "phoneName" to editablePhoneName,
                                        "scheduledDate" to editableScheduledDate,
                                        "shorthand" to editableShorthand,
                                        "start" to editableStart,
                                        "status" to editableStatus,
                                        "street" to editableStreet,
                                        "takenBy" to editableTakenBy,
                                        "techHelper" to editableTechHelper,
                                        "techHelperId" to techHelperGeneratedId,
                                        "techLead" to editableTechLead,
                                        "timeAlotted" to editableTimeAllotted,
                                        "timeOfDay" to editableTimeOfDay,
                                        "title" to editableTitle
                                    )
                                    db.collection("events").document(techLeadGeneratedId)
                                        .set(docData)
                                        .addOnSuccessListener {
                                            Log.d("firebase", "DocumentSnapshot written with ID: $techLeadGeneratedId")
                                            Toast.makeText(context, "Scheduled $editableTechLead", Toast.LENGTH_SHORT).show()

                                            val docDataTwo = hashMapOf(
                                                "id" to techHelperGeneratedId,
                                                "altPhoneName" to editableAltPhoneName,
                                                "altphone" to editableAltPhone,
                                                "city" to editableCity,
                                                "customerId" to customerId,
                                                "dateCreated" to editableDateCreated,
                                                "dateModified" to editableDateModified,
                                                "dateScheduled" to editableDateScheduled,
                                                "end" to editableEnd,
                                                "firstname" to editableFirstName,
                                                "issue" to editableIssue,
                                                "jobNumber" to editableJobNumber,
                                                "lastname" to editableLastName,
                                                "leadSource" to editableLeadSource,
                                                "notes" to editableNotes,
                                                "payment" to editablePayment,
                                                "phone" to editablePhone,
                                                "phoneName" to editablePhoneName,
                                                "scheduledDate" to editableScheduledDate,
                                                "shorthand" to editableShorthand,
                                                "start" to editableStart,
                                                "status" to editableStatus,
                                                "street" to editableStreet,
                                                "takenBy" to editableTakenBy,
                                                "techHelper" to editableTechLead,
                                                "techHelperId" to techLeadGeneratedId,
                                                "techLead" to editableTechHelper,
                                                "timeAlotted" to editableTimeAllotted,
                                                "timeOfDay" to editableTimeOfDay,
                                                "title" to editableTitle
                                            )

                                            db.collection("events").document(techHelperGeneratedId)
                                                .set(docDataTwo)
                                                .addOnSuccessListener {
                                                    Log.d("firebase", "DocumentSnapshot written with ID: $techHelperGeneratedId")
                                                    Toast.makeText(context, "Scheduled $editableTechHelper", Toast.LENGTH_SHORT).show()
                                                }
                                                .addOnFailureListener {
                                                    Log.w("firebase", "Error adding document", it)
                                                    Toast.makeText(context, "Failed to add $editableTechHelper", Toast.LENGTH_SHORT).show()
                                                }

                                            finish()
                                        }
                                        .addOnFailureListener { e ->
                                            Log.w("firebase", "Error adding document", e)
                                            Toast.makeText(context, "Failed to add $editableTechLead", Toast.LENGTH_SHORT).show()
                                            finish()
                                        }
                                } else {
                                    //Single Service Tech add
                                    val docData = hashMapOf(
                                        "id" to techLeadGeneratedId,
                                        "altPhoneName" to editableAltPhoneName,
                                        "altphone" to editableAltPhone,
                                        "city" to editableCity,
                                        "customerId" to customerId,
                                        "dateCreated" to editableDateCreated,
                                        "dateModified" to editableDateModified,
                                        "dateScheduled" to editableDateScheduled,
                                        "end" to editableEnd,
                                        "firstname" to editableFirstName,
                                        "issue" to editableIssue,
                                        "jobNumber" to editableJobNumber,
                                        "lastname" to editableLastName,
                                        "leadSource" to editableLeadSource,
                                        "notes" to editableNotes,
                                        "payment" to editablePayment,
                                        "phone" to editablePhone,
                                        "phoneName" to editablePhoneName,
                                        "scheduledDate" to editableScheduledDate,
                                        "shorthand" to editableShorthand,
                                        "start" to editableStart,
                                        "status" to editableStatus,
                                        "street" to editableStreet,
                                        "takenBy" to editableTakenBy,
                                        "techHelper" to editableTechHelper,
                                        "techHelperId" to editableTechHelperId,
                                        "techLead" to editableTechLead,
                                        "timeAlotted" to editableTimeAllotted,
                                        "timeOfDay" to editableTimeOfDay,
                                        "title" to editableTitle
                                    )
                                    db.collection("events").document(techLeadGeneratedId)
                                        .set(docData)
                                        .addOnSuccessListener {
                                            Log.d("firebase", "DocumentSnapshot written with ID: $techLeadGeneratedId")
                                            Toast.makeText(context, "Scheduled $editableTechLead", Toast.LENGTH_SHORT).show()
                                            finish()
                                        }
                                        .addOnFailureListener { e ->
                                            Log.w("firebase", "Error adding document", e)
                                            Toast.makeText(context, "Failed to add $editableTechLead", Toast.LENGTH_SHORT).show()
                                            finish()
                                        }
                                }
                            }
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = "Submit",
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