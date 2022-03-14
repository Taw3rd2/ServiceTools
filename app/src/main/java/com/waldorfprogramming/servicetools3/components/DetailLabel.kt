package com.waldorfprogramming.servicetools3.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.waldorfprogramming.servicetools3.R

@Composable
fun DetailLabel(
    label: String,
    clickable: Boolean = false
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.primary)
    ) {
        if(clickable){
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = Modifier.weight(.5f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = label,
                        color = Color.White,
                        style = MaterialTheme.typography.subtitle1,
                    )
                }
                Column (
                    modifier = Modifier.weight(.5f),
                    horizontalAlignment = Alignment.End
                        ){
                    Icon(
                        modifier = Modifier.padding(8.dp),
                        painter = painterResource(id = R.drawable.ic_clickable),
                        contentDescription = "clickable",
                        tint = Color.White
                    )
                }
            }
        } else {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = label,
                color = Color.White,
                style = MaterialTheme.typography.subtitle1,
            )
        }

    }
}