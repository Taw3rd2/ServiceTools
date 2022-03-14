package com.waldorfprogramming.servicetools3.daily_service.dispatch_create.work_list_drop_down

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

@ExperimentalCoroutinesApi
@Composable
fun WorkListDropDown(
    viewModel: WorkListDropDownViewModel,
    setEditable: (String) -> Unit,
    setEditableShorthand: (String) -> Unit,
    selectedEditable: String,
    label: String,
) {
    var workList: MutableList<WorkListItemModel> = arrayListOf()
    when(val workListFlow = viewModel
        .workListStateFlow
        .asStateFlow()
        .collectAsState()
        .value){
        is OnError -> {
            Log.d("firebase", "Dispatch Create: some thing went wrong")
            Text(text = "try again when you have a better signal")
        }
        is OnSuccess -> {
            val listOfWork =
                workListFlow.querySnapshot?.toObjects(WorkListItemModel::class.java)
            listOfWork?.let {
                workList = it
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

    var expanded by remember { mutableStateOf(false) }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }
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
                .padding(horizontal =  16.dp)
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
            workList.forEach { item ->
                DropdownMenuItem(onClick = {
                    setEditable(item.item)
                    setEditableShorthand(item.shorthand)
                    expanded = false
                }) {
                    Text(
                        text = item.item,
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }
}