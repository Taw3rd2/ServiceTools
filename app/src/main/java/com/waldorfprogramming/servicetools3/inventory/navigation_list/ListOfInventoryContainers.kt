package com.waldorfprogramming.servicetools3.inventory.navigation_list

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.waldorfprogramming.servicetools3.components.GradientButton
import com.waldorfprogramming.servicetools3.inventory.inventory_containers.InventoryContainerModel
import com.waldorfprogramming.servicetools3.ui.theme.buttonGradientColor1
import com.waldorfprogramming.servicetools3.ui.theme.buttonGradientColor2

@ExperimentalFoundationApi
@Composable
fun ListOfInventoryContainers( inventoryContainers: List<InventoryContainerModel>) {

    val context = LocalContext.current

    if(inventoryContainers.isNullOrEmpty()){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn{
            items(
                items = inventoryContainers,
                key = {it.containerId}
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
                    GradientButton(
                        text = it.containerName,
                        textColor = MaterialTheme.colors.onSecondary,
                        gradient = Brush.horizontalGradient(
                            colors = listOf(
                                buttonGradientColor1,
                                buttonGradientColor2
                            )
                        ),
                        onClick = {
                            //TODO
                        }
                    )
                }
            }
        }
    }
}