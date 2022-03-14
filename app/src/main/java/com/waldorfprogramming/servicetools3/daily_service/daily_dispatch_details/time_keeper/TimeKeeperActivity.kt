package com.waldorfprogramming.servicetools3.daily_service.daily_dispatch_details.time_keeper

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.firestore.FirebaseFirestore
import com.waldorfprogramming.servicetools3.R
import com.waldorfprogramming.servicetools3.components.DetailCard
import com.waldorfprogramming.servicetools3.components.DetailLabel
import com.waldorfprogramming.servicetools3.components.ServiceToolsTopBar
import com.waldorfprogramming.servicetools3.daily_service.daily_dispatch_details.DailyDispatchDetailsViewModel
import com.waldorfprogramming.servicetools3.ui.theme.*
import com.waldorfprogramming.servicetools3.utilities.javaDateToTimeString
import java.text.DecimalFormat
import java.util.*

class TimeKeeperActivity: AppCompatActivity() {

    private var dispatchId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.time_keeper)

        val bundle = intent.extras
        if (bundle != null) {
            dispatchId = bundle.getString("DispatchId").toString()
        }

        val viewModel: DailyDispatchDetailsViewModel by viewModels()

        val timeKeeperComposeView = findViewById<ComposeView>(R.id.time_keeper_compose_view)
        timeKeeperComposeView.setContent {
            ServiceTools3Theme {

                var start by remember { mutableStateOf(Date())}
                var end by remember { mutableStateOf(Date())}

                start = viewModel.dispatchState.value.start
                end = viewModel.dispatchState.value.end

                var jobStatusText by remember { mutableStateOf("")}
                var statusSetterExpanded by remember { mutableStateOf(false)}
                var jobStatusIndex: Int by rememberSaveable { mutableStateOf(0)}
                val jobStatus = listOf(
                    "Scheduled",
                    "Active",
                    "Parts Needed",
                    "Done"
                )
                var jobStatusColor by remember { mutableStateOf(scheduledJob)}

                when (viewModel.dispatchState.value.status) {
                    "scheduled" -> {
                        jobStatusColor = scheduledJob
                        jobStatusText = "Scheduled"
                    }
                    "active" -> {
                        jobStatusColor = activeJob
                        jobStatusText = "Active"
                    }
                    "parts" -> {
                        jobStatusColor = partsNeededJob
                        jobStatusText = "Parts Needed"
                    }
                    "done" -> {
                        jobStatusColor = doneJob
                        jobStatusText = "Done"
                    }
                }

                Scaffold(
                    topBar = {
                        ServiceToolsTopBar(title = "Time Keeper")
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(top = 32.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                            ) {
                                Column(modifier = Modifier.weight(.5f)) {
                                    //Allotted Time
                                    DetailCard(
                                        labelText = "Slotted Time",
                                        dataText = "${viewModel.dispatchState.value.timeAlotted} hrs",
                                        data = true
                                    )
                                    Spacer(modifier = Modifier.padding(top = 4.dp))
                                    //Start Time
                                    DetailCard(
                                        labelText = "Start Time",
                                        dataText = javaDateToTimeString(start),
                                        data = true
                                    )

                                    //Start Button
                                    Button(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(2.dp),
                                        shape = RoundedCornerShape(5.dp),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                                        onClick = { openStartTimePicker()}
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(8.dp),
                                            text = "Set Start Time",
                                            color = Color.White,
                                            style = MaterialTheme.typography.button,
                                            fontSize = 18.sp
                                        )
                                    }
                                }
                                Column(modifier = Modifier.weight(.5f)) {
                                    //Time Accumulated
                                    DetailCard(
                                        labelText = "Actual Time",
                                        dataText = "${getActualTime(
                                            viewModel.dispatchState.value.start,
                                            viewModel.dispatchState.value.end
                                            )} hrs",
                                        data = true
                                    )
                                    Spacer(modifier = Modifier.padding(top = 4.dp))
                                    //End Time
                                    DetailCard(
                                        labelText = "Stop Time",
                                        dataText = javaDateToTimeString(end),
                                        data = true
                                    )

                                    //End Button
                                    Button(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(2.dp),
                                        shape = RoundedCornerShape(5.dp),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                                        onClick = {
                                            openEndTimePicker()
                                        }
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(8.dp),
                                            text = "Set Stop Time",
                                            color = Color.White,
                                            style = MaterialTheme.typography.button,
                                            fontSize = 18.sp
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.padding(top = 16.dp))

                            //Job Status
                            DetailLabel(label = "Job Status")
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = jobStatusText,
                                style = MaterialTheme.typography.h4,
                                color = jobStatusColor
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { statusSetterExpanded = true }
                                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                                    .background(color = MaterialTheme.colors.onSecondary)
                                    .border(1.dp, MaterialTheme.colors.onSurface),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { statusSetterExpanded = true }) {
                                    Icon(
                                        Icons.Default.Build,
                                        contentDescription = "Localized description"
                                    )
                                }
                                DropdownMenu(
                                    expanded = statusSetterExpanded,
                                    onDismissRequest = { statusSetterExpanded = false }
                                ) {
                                    jobStatus.forEachIndexed { index, string ->
                                        DropdownMenuItem(
                                            onClick = {
                                                jobStatusIndex = index
                                                statusSetterExpanded = false
                                            }
                                        ) {
                                            Text(text = string)
                                        }
                                    }
                                }
                                Text(
                                    text = jobStatus[jobStatusIndex],
                                    style = MaterialTheme.typography.h6
                                )
                            }



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
                                onClick = { updateJobStatus(jobStatusIndex)}
                            ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = "Update Status",
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

    private fun openStartTimePicker(){

        val calendar = Calendar.getInstance()
        val hr = calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)

        val clockFormat = TimeFormat.CLOCK_12H
        val startPicker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(hr)
            .setMinute(min)
            .setTitleText("Set Start Time")
            .build()
        startPicker.show(supportFragmentManager, "startPickerShow")

        startPicker.addOnPositiveButtonClickListener {
            val h = startPicker.hour
            val m = startPicker.minute

            val startCalendar = Calendar.getInstance()
            startCalendar.set(Calendar.HOUR_OF_DAY, h)
            startCalendar.set(Calendar.MINUTE, m)
            val startDate = startCalendar.time

            val db = FirebaseFirestore.getInstance()
            val collection = db
                .collection("events")
                .document(dispatchId)
            collection
                .update("start", startDate)
                .addOnSuccessListener {
                    Toast.makeText(this, "Start Time Updated", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Start Time Failed", Toast.LENGTH_SHORT).show()
                }
        }
        startPicker.addOnNegativeButtonClickListener {
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()

        }
    }

    private fun openEndTimePicker(){

        val calendar = Calendar.getInstance()
        val hr = calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)

        val clockFormat = TimeFormat.CLOCK_12H
        val endPicker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(hr)
            .setMinute(min)
            .setTitleText("Set Stop Time")
            .build()
        endPicker.show(supportFragmentManager, "endPickerShow")

        endPicker.addOnPositiveButtonClickListener {
            val h = endPicker.hour
            val m = endPicker.minute

            val endCalendar = Calendar.getInstance()
            endCalendar.set(Calendar.HOUR_OF_DAY, h)
            endCalendar.set(Calendar.MINUTE, m)
            val endDate = endCalendar.time

            val db = FirebaseFirestore.getInstance()
            val collection = db
                .collection("events")
                .document(dispatchId)
            collection
                .update("end", endDate)
                .addOnSuccessListener {
                    Toast.makeText(this, "End Time Updated", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "End Time Failed", Toast.LENGTH_SHORT).show()
                }
        }
        endPicker.addOnNegativeButtonClickListener {
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateJobStatus(statusIndex: Int){
        var status = ""
        when (statusIndex) {
            0 -> status = "scheduled"
            1 -> status = "active"
            2 -> status = "parts"
            3 -> status = "done"
        }
        val db = FirebaseFirestore.getInstance()
        val collection = db
            .collection("events")
            .document(dispatchId)
        collection
            .update("status", status)
            .addOnSuccessListener {
                Toast.makeText(this, "Status Updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Status Update Failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getActualTime(startTime: Date, endTime: Date): String{
        val longStartTime = startTime.time
        val longEndTime = endTime.time
        var doubleTotalTime = longEndTime.toDouble() - longStartTime.toDouble()
        doubleTotalTime /= 1000
        val totalTime = doubleTotalTime / 3600
        return DecimalFormat("##.##").format(totalTime)
    }
}