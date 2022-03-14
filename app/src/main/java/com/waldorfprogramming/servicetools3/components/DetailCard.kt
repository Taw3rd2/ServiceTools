package com.waldorfprogramming.servicetools3.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailCard(
    labelText: String,
    dataText: String,
    data: Boolean
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .padding(2.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ){
        Text(
            modifier = Modifier.padding(8.dp),
            text = labelText,
            style = MaterialTheme.typography.caption,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(data){
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = dataText,
                    style = MaterialTheme.typography.h6,
                )
            } else {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = dataText,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}