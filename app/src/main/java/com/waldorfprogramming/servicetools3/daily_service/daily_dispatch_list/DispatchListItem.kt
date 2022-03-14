package com.waldorfprogramming.servicetools3.daily_service.daily_dispatch_list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.waldorfprogramming.servicetools3.daily_service.DispatchModel
import com.waldorfprogramming.servicetools3.ui.theme.CAUTIONCOLOR

@Composable
fun DispatchListItem(
    dispatch: DispatchModel,
    onClick: () -> Unit
    ) {

    var borderColor by remember { mutableStateOf(Color.Gray)}
    var cardText by remember { mutableStateOf(Color.White)}

    when (dispatch.status) {
        "scheduled" -> {
            borderColor = Color.Blue
            cardText = Color.Black
        }
        "active" -> {
            borderColor = Color.Green
            cardText = Color.White
        }
        "parts" -> {
            borderColor = CAUTIONCOLOR
            cardText = Color.Black
        }
        "done" -> {
            borderColor = Color.Gray
            cardText = Color.Black
        }
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 5.dp,
        modifier = Modifier
            .padding(5.dp)
            .border(2.dp, borderColor)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.background)

                .padding(8.dp)
        ) {
            Row {
                if(dispatch.firstname.isNotEmpty() && dispatch.lastname.isNotEmpty()){
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.Start),
                        text = "${dispatch.firstname} ${dispatch.lastname}",
                        fontSize = 22.sp,
                        color = cardText
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.Start),
                        text = dispatch.lastname,
                        fontSize = 22.sp,
                        color = cardText
                    )
                }
                if(dispatch.timeOfDay.isNotEmpty()){
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.End),
                        text = dispatch.timeOfDay,
                        fontSize = 22.sp,
                        color = cardText
                    )
                }
            }
            Row {
                if(dispatch.techLead.isNotEmpty()){
                    if(dispatch.techHelper != "NONE"){
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentWidth(Alignment.Start),
                            text = "${dispatch.techLead} with ${dispatch.techHelper}",
                            fontStyle = FontStyle.Italic,
                            color = cardText
                        )
                    } else {
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentWidth(Alignment.Start),
                            text = dispatch.techLead,
                            fontStyle = FontStyle.Italic,
                            color = cardText
                        )
                    }
                }
                if(dispatch.timeAlotted.isNotEmpty()){
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.End),
                        text = "${dispatch.timeAlotted} hours slotted",
                        fontStyle = FontStyle.Italic,
                        color = cardText
                    )
                }
            }
        }
    }
}