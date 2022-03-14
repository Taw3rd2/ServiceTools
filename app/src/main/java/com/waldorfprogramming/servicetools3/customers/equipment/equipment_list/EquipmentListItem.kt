package com.waldorfprogramming.servicetools3.customers.equipment.equipment_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.waldorfprogramming.servicetools3.customers.equipment.EquipmentModel

@Composable
fun EquipmentListItem(
    equipment: EquipmentModel,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 5.dp,
        modifier = Modifier
            .padding(5.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(color = MaterialTheme.colors.background)
        ) {
            Row {
                if(equipment.equipmentName.isNotEmpty()){
                    Text(
                        text = equipment.equipmentName,
                        fontSize = 22.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
            Row {
                if(equipment.equipmentBrand.isNotEmpty()){
                    Text(
                        text = equipment.equipmentBrand,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}