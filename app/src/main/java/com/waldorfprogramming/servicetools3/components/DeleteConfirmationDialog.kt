package com.waldorfprogramming.servicetools3.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.waldorfprogramming.servicetools3.R
import com.waldorfprogramming.servicetools3.ui.theme.CAUTIONCOLOR

@Composable
fun DeleteConfirmationDialog(
    openDeleteConfirmationDialog: MutableState<Boolean>,
    delete: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 8.dp
    ) {
        Column(modifier = Modifier.background(Color.White)) {
            Image(
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = "Notification",
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(color = CAUTIONCOLOR),
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth()
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Are you sure?",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.h4,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "This is a permanent removal from the Firestore Database!",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.body2
                )

            }
            
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    //.background(MaterialTheme.colors.background)
                ,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(
                    onClick = { openDeleteConfirmationDialog.value = false }
                ) {
                    Text(
                        text = "Not Now",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
                }
                TextButton(onClick = {
                    delete()
                    openDeleteConfirmationDialog.value = false
                }) {
                    Text(
                        text = "Allow",
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                }
            }
        }
    }
}