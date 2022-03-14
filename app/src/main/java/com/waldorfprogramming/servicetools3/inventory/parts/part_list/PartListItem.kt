package com.waldorfprogramming.servicetools3.inventory.parts.part_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.waldorfprogramming.servicetools3.inventory.parts.PartModel
import com.waldorfprogramming.servicetools3.utilities.longToStringCurrency
import com.waldorfprogramming.servicetools3.utilities.longToStringCurrencyWithMarkup

@Composable
fun PartListItem(
    part: PartModel,
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
                if(part.partNumber.isNotEmpty()){
                    Text(
                        modifier = Modifier
                            .weight(1F)
                            .wrapContentWidth(Alignment.Start),
                        text = part.partNumber,
                        fontSize = 22.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                }
                if(part.partCost != null){
                    Text(
                        modifier = Modifier
                            .weight(1F)
                            .wrapContentWidth(Alignment.End),
                        text = longToStringCurrencyWithMarkup(part.partCost, 1.75),
                        fontSize = 22.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
            Row {
                if(part.partVendor.isNotEmpty()){
                    Text(
                        modifier = Modifier
                            .weight(1F)
                            .wrapContentWidth(Alignment.Start),
                        text = part.partVendor,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colors.primary
                    )
                }
                if(part.partDataDate.isNotEmpty()){
                    Text(
                        modifier = Modifier
                            .weight(1F)
                            .wrapContentWidth(Alignment.End),
                        text = part.partDataDate,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}