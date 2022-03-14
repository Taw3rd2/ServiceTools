package com.waldorfprogramming.servicetools3.customers.equipment.equipment_list

import android.content.Intent
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.waldorfprogramming.servicetools3.adapters.OnError
import com.waldorfprogramming.servicetools3.adapters.OnSuccess
import com.waldorfprogramming.servicetools3.components.FABAddContent
import com.waldorfprogramming.servicetools3.components.ServiceToolsTopBar
import com.waldorfprogramming.servicetools3.customers.equipment.EquipmentModel
import com.waldorfprogramming.servicetools3.customers.equipment.equipment_create.EquipmentCreate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asStateFlow

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun EquipmentListScreen(
    viewModel: EquipmentListViewModel,
    customerFirstName: String,
    customerLastName: String
) {
    val context = LocalContext.current
    val customerId = viewModel.customerId

    when (val equipmentList = viewModel.equipmentStateFlow.asStateFlow().collectAsState().value) {

        is OnError -> {
            Log.d("firebase", "EquipmentListScreen: some thing went wrong")
            Text(text = "try again when you have a better signal")
        }
        is OnSuccess -> {
            val listOfEquipment = equipmentList.querySnapshot?.toObjects(EquipmentModel::class.java)
            listOfEquipment?.let {
                Scaffold(
                    topBar = {
                        ServiceToolsTopBar(title = "Equipment List")
                    },
                    floatingActionButton = {
                        FABAddContent(
                            contentDescription = "Add New Equipment") {
                            val createNewEquipmentIntent = Intent(context, EquipmentCreate::class.java)
                            createNewEquipmentIntent.putExtra("CustomerId", customerId)
                            context.startActivity(createNewEquipmentIntent)
                        }
                    }) {
                    ListOfEquipment(
                        customerId,
                        customerFirstName,
                        customerLastName,
                        equipment = listOfEquipment
                    )
                }

            }
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}