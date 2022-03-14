package com.waldorfprogramming.servicetools3.customers.equipment.equipment_list

import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.waldorfprogramming.servicetools3.customers.equipment.EquipmentModel
import com.waldorfprogramming.servicetools3.customers.equipment.equipment_details.EquipmentDetailsActivity

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun ListOfEquipment(
    customerId: String,
    customerFirstname: String,
    customerLastName: String,
    equipment: List<EquipmentModel>
) {
    val context = LocalContext.current
    LazyColumn {
        items(
            items = equipment,
            key = { it.id }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItemPlacement(
                        animationSpec = tween(
                            durationMillis = 500
                        )
                    )
                    .padding(top = 4.dp, start = 4.dp, end = 4.dp)
            ) {
                EquipmentListItem(it) {
                    val equipmentDetailsIntent =
                    Intent(context, EquipmentDetailsActivity::class.java)
                        equipmentDetailsIntent.putExtra("CustomerId", customerId )
                        equipmentDetailsIntent.putExtra("EquipmentId", it.id)
                        equipmentDetailsIntent.putExtra("customerFirstName", customerFirstname)
                        equipmentDetailsIntent.putExtra("customerLastName", customerLastName)
                    context.startActivity(equipmentDetailsIntent)
                }
            }
        }
    }
}