package com.waldorfprogramming.servicetools3.daily_service.dispatch_create.technician_drop_down

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.waldorfprogramming.servicetools3.adapters.OnError
import com.waldorfprogramming.servicetools3.adapters.OnSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asStateFlow

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun TechnicianDropDown(
    viewModel: TechnicianDropDownViewModel,
    setEditable: (String) -> Unit,
    setEditableId: (String) -> Unit,
    selectedEditable: String,
    label: String,
) {

    var technicianList: MutableList<TechnicianListItemModel> = arrayListOf()
    when(val technicianListFlow = viewModel
        .techListStateFlow
        .asStateFlow()
        .collectAsState()
        .value){
        is OnError -> {
            Log.d("firebase", "Dispatch Create: some thing went wrong")
            Text(text = "try again when you have a better signal")
        }
        is OnSuccess -> {
            val listOfTechnicians =
                technicianListFlow.querySnapshot?.toObjects(TechnicianListItemModel::class.java)
            listOfTechnicians?.let {
                technicianList = it
            }
        } else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }

    var expanded by remember { mutableStateOf(false)}
    var textfieldSize by remember { mutableStateOf(Size.Zero)}
    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column {
            OutlinedTextField(
                value = selectedEditable,
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                    .onGloballyPositioned { coordinates ->
                        textfieldSize = coordinates.size.toSize()
                    }
                    .clickable { expanded = !expanded },
                label = {Text(label)},
                trailingIcon = {
                    Icon(icon,"contentDescription",
                        Modifier.clickable { expanded = !expanded })
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current){textfieldSize.width.toDp()})
            ) {
                technicianList.forEach { tech ->
                    DropdownMenuItem(onClick = {
                        setEditable(tech.name)
                        setEditableId(tech.technicianListItemId)
                        expanded = false
                    }) {
                        Text(
                            text = tech.name,
                            style = MaterialTheme.typography.h6
                        )
                    }
                }
            }
        }

//        ExposedDropdownMenuBox(
//            expanded = expanded,
//            onExpandedChange = {
//                expanded = !expanded
//            }
//        ) {
//            TextField(
//                readOnly = true,
//                value = selectedOptionText,
//                onValueChange = { },
//                label = Text(label),
////                trailingIcon = {
////                    ExposedDropdownMenuDefaults.TrailingIcon(
////                        expanded = expanded
////                    )
////                },
//                colors = ExposedDropdownMenuDefaults.textFieldColors()
//            )
//            ExposedDropdownMenu(
//                expanded = expanded,
//                onDismissRequest = {
//                    expanded = false
//                }
//            ) {
//                technicianList.forEach { selectionOption ->
//                    DropdownMenuItem(
//                        onClick = {
//                            setEditable(selectionOption.name)
//                            selectedOptionText = selectionOption
//                            expanded = false
//                        }
//                    ) {
//                        Text(text = selectionOption.name)
//                    }
//                }
//            }
//        }

//        DropdownMenu(
//            modifier = Modifier
//                .width(with(LocalDensity.current){textFieldSize.width.toDp()}),
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            technicianList.forEach { string ->
//                DropdownMenuItem(
//                    onClick = {
//                        setEditable(string.name)
//                        expanded = false
//                    }
//                ) {
//                    Text(
//                        text = string.name,
//                        style = MaterialTheme.typography.h6
//                    )
//                }
//            }
//        }
}
