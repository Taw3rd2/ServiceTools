package com.waldorfprogramming.servicetools3.charging_tool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.waldorfprogramming.servicetools3.components.ServiceToolsTopBar
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme
import com.waldorfprogramming.servicetools3.ui.theme.buttonGradientColor1
import com.waldorfprogramming.servicetools3.ui.theme.buttonGradientColor2
import java.lang.Math.floor

@ExperimentalComposeUiApi
class ChargingTool: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ServiceTools3Theme {
                var liquidExpanded by remember { mutableStateOf(false) }
                var suctionExpanded by remember { mutableStateOf(false) }
                var freonExpanded by remember { mutableStateOf(false) }

                var freonTypeIndex: Int by rememberSaveable { mutableStateOf(1) }
                val freonType = listOf(
                    "R22 Freon", "R410A Freon"
                )

                var liquidSizeIndex: Int by rememberSaveable { mutableStateOf(2) }
                val liquidLineSize = listOf(
                    "1/4\" Liquid Line",
                    "5/16\" Liquid Line",
                    "3/8\" Liquid Line",
                    "1/2\" Liquid Line",
                    "5/8\" Liquid Line",
                    "7/8\" Liquid Line",
                )

                var suctionSizeIndex: Int by rememberSaveable { mutableStateOf(2) }
                val suctionLineSize = listOf(
                    "1/2\" Suction Line",
                    "5/8\" Suction Line",
                    "3/4\" Suction Line",
                    "7/8\" Suction Line",
                    "1-1/8\" Suction Line",
                    "1-3/8\" Suction Line",
                    "1-5/8\" Suction Line",
                    "2-1/8\" Suction Line"
                )

                var liquidLineMultiplier: Double by rememberSaveable { mutableStateOf(0.0) }
                var suctionLineMultiplier: Double by rememberSaveable { mutableStateOf(0.0) }

                if(freonTypeIndex == 0){
                    // R22
                    when (liquidSizeIndex) {
                        0 -> liquidLineMultiplier = 0.23
                        1 -> liquidLineMultiplier = 0.40
                        2 -> liquidLineMultiplier = 0.62
                        3 -> liquidLineMultiplier = 1.12
                        4 -> liquidLineMultiplier = 1.81
                        5 -> liquidLineMultiplier = 3.78
                    }
                    when (suctionSizeIndex) {
                        0 -> suctionLineMultiplier = 0.02
                        1 -> suctionLineMultiplier = 0.04
                        2 -> suctionLineMultiplier = 0.06
                        3 -> suctionLineMultiplier = 0.08
                        4 -> suctionLineMultiplier = 0.14
                        5 -> suctionLineMultiplier = 0.21
                        6 -> suctionLineMultiplier = 0.30
                        7 -> suctionLineMultiplier = 0.53
                    }
                } else {
                    // R410A
                    when (liquidSizeIndex) {
                        0 -> liquidLineMultiplier = 0.19
                        1 -> liquidLineMultiplier = 0.33
                        2 -> liquidLineMultiplier = 0.51
                        3 -> liquidLineMultiplier = 1.01
                        4 -> liquidLineMultiplier = 1.64
                        5 -> liquidLineMultiplier = 2.46
                    }
                    when (suctionSizeIndex) {
                        0 -> suctionLineMultiplier = 0.04
                        1 -> suctionLineMultiplier = 0.06
                        2 -> suctionLineMultiplier = 0.09
                        3 -> suctionLineMultiplier = 0.12
                        4 -> suctionLineMultiplier = 0.20
                        5 -> suctionLineMultiplier = 0.31
                        6 -> suctionLineMultiplier = 0.43
                        7 -> suctionLineMultiplier = 0.76
                    }
                }

                var feet: String by rememberSaveable { mutableStateOf("15.0") }
                val difference: Boolean
                val totalCharge: Double

                val doubleFeet: Double = if (feet.isEmpty()) {
                    0.0
                } else {
                    feet.toDouble()
                }

                if (doubleFeet >= 15.0) {
                    difference = true
                    val chargeTotal = suctionLineMultiplier + liquidLineMultiplier
                    val total = (doubleFeet - 15.0) * chargeTotal
                    totalCharge = floor(total * 100) / 100
                } else {
                    difference = false
                    val sub = 15.0 - doubleFeet
                    val chargeTotal = suctionLineMultiplier + liquidLineMultiplier
                    val subTwo = sub * chargeTotal
                    totalCharge = floor(subTwo * 100) / 100
                }
                val chargeOrRecover: String = if (difference) {
                    "Charge "
                } else {
                    "Recover"
                }

                val ounces = "$chargeOrRecover $totalCharge"
                val scaffoldState = rememberScaffoldState()
                val keyboardController = LocalSoftwareKeyboardController.current
                //val relocation = remember { BringIntoViewRequester() }
                //val scope = rememberCoroutineScope()

                Scaffold(
                    topBar = {
                        ServiceToolsTopBar(title = "Charging Calculator")
                    },
                    scaffoldState = scaffoldState
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .background(MaterialTheme.colors.background)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, end = 8.dp, start = 8.dp)
                                .border(1.dp, MaterialTheme.colors.onSurface) //black
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Brush.horizontalGradient(
                                            colors = listOf(
                                                buttonGradientColor1,
                                                buttonGradientColor2
                                            )
                                        )
                                    )
                                    //.background(Color(0xFF009688)) //primary
                                    .padding(vertical = 16.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .wrapContentSize(Alignment.Center)
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "Select Freon Type",
                                            style = MaterialTheme.typography.h5,
                                            color = MaterialTheme.colors.onSecondary,
                                            modifier = Modifier
                                                .padding(bottom = 16.dp)
                                        )
                                        Row(modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { freonExpanded = true }
                                            .padding(horizontal = 16.dp)
                                            .background(color = MaterialTheme.colors.onSecondary)
                                            .border(1.dp, MaterialTheme.colors.onSurface), //black
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            IconButton(onClick = { freonExpanded = true }) {
                                                Icon(
                                                    Icons.Default.Build,
                                                    contentDescription = "Localized description"
                                                )
                                            }
                                            DropdownMenu(
                                                expanded = freonExpanded,
                                                onDismissRequest = { freonExpanded = false },
                                            ) {
                                                freonType.forEachIndexed { index, string ->
                                                    DropdownMenuItem(
                                                        onClick = {
                                                            freonTypeIndex = index
                                                            freonExpanded = false
                                                        }
                                                    ) {
                                                        Text(text = string)
                                                    }
                                                }
                                            }
                                            Text(freonType[freonTypeIndex])
                                        }
                                        Text(
                                            text = "Select Line Set Line Sizes",
                                            style = MaterialTheme.typography.h5,
                                            color = MaterialTheme.colors.onSecondary,
                                            modifier = Modifier
                                                .padding(bottom = 16.dp, top = 16.dp)
                                        )
                                        Row(modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { liquidExpanded = true }
                                            .padding(horizontal = 16.dp)
                                            .background(color = MaterialTheme.colors.onSecondary)
                                            .border(1.dp, MaterialTheme.colors.onSurface), //black
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            IconButton(onClick = { liquidExpanded = true }) {
                                                Icon(
                                                    Icons.Default.Build,
                                                    contentDescription = "Localized description"
                                                )
                                            }
                                            DropdownMenu(
                                                expanded = liquidExpanded,
                                                onDismissRequest = { liquidExpanded = false },
                                            ) {
                                                liquidLineSize.forEachIndexed { index, string ->
                                                    DropdownMenuItem(
                                                        onClick = {
                                                            liquidSizeIndex = index
                                                            liquidExpanded = false
                                                        }
                                                    ) {
                                                        Text(text = string)
                                                    }
                                                }
                                            }
                                            Text(liquidLineSize[liquidSizeIndex])
                                        }
                                        Row(modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { suctionExpanded = true }
                                            .padding(16.dp)
                                            .background(color = MaterialTheme.colors.onSecondary)
                                            .border(1.dp, MaterialTheme.colors.onSurface), //black
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            IconButton(onClick = { suctionExpanded = true }) {
                                                Icon(
                                                    Icons.Default.Build,
                                                    contentDescription = "Localized description"
                                                )
                                            }
                                            DropdownMenu(
                                                expanded = suctionExpanded,
                                                onDismissRequest = { suctionExpanded = false },
                                            ) {
                                                suctionLineSize.forEachIndexed { index, string ->
                                                    DropdownMenuItem(
                                                        onClick = {
                                                            suctionSizeIndex = index
                                                            suctionExpanded = false
                                                        }
                                                    ) {
                                                        Text(text = string)
                                                    }
                                                }
                                            }
                                            Text(suctionLineSize[suctionSizeIndex])
                                        }
                                    }
                                }
                            }
                        }
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, end = 8.dp, start = 8.dp)
                                .border(1.dp, MaterialTheme.colors.onSurface) //black
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Brush.horizontalGradient(
                                            colors = listOf(
                                                buttonGradientColor1,
                                                buttonGradientColor2
                                            )
                                        )
                                    ) //primary
                                    .padding(vertical = 16.dp)
                            ) {
                                Text(
                                    text = "Input Line Set Length In Feet",
                                    style = MaterialTheme.typography.h5,
                                    color = MaterialTheme.colors.onSecondary,
                                    modifier = Modifier
                                        .padding(bottom = 16.dp, top = 8.dp)
                                )
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
//                                        .bringIntoViewRequester(relocation)
//                                        .onFocusEvent {
//                                            if(it.isFocused) scope.launch { delay(300); relocation.bringIntoView() }
//                                        },
                                    value = feet,
                                    onValueChange = {
                                        feet = it
                                    },
                                    label = { Text(text = "Feet") },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Done
                                    ),
                                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                                    singleLine = true,
                                    leadingIcon = {
                                        Icon(Icons.Filled.Edit, contentDescription = "edit")
                                    },
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        textColor = MaterialTheme.colors.onSecondary,
                                        leadingIconColor= MaterialTheme.colors.onSecondary,
                                        focusedBorderColor = MaterialTheme.colors.onSecondary,
                                        unfocusedBorderColor = MaterialTheme.colors.onSurface,
                                        focusedLabelColor = MaterialTheme.colors.onSecondary,
                                        unfocusedLabelColor = MaterialTheme.colors.onSurface,
                                        cursorColor = MaterialTheme.colors.onSurface,
                                    )
                                )
                                Text(
                                    text = "$ounces ounces",
                                    modifier = Modifier
                                        .padding(top = 16.dp, bottom = 8.dp),
                                    style = MaterialTheme.typography.h4,
                                    color = MaterialTheme.colors.onSecondary,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}