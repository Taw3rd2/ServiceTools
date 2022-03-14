package com.waldorfprogramming.servicetools3.inventory.parts.part_list

import android.content.Intent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.waldorfprogramming.servicetools3.inventory.parts.PartModel
import com.waldorfprogramming.servicetools3.inventory.parts.part_details.PartDetailsActivity

@ExperimentalFoundationApi
@Composable
fun ListOfParts ( parts: List<PartModel>){

    val context = LocalContext.current

    if(parts.isNullOrEmpty()){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn{
            items(
                items = parts,
                key = {it.partId}
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
                    PartListItem(part = it) {
                        val partDetailsIntent =
                            Intent(context, PartDetailsActivity::class.java)
                        partDetailsIntent.putExtra("UpdatePartId", it.partId)
                        context.startActivity(partDetailsIntent)
                    }
                }
            }
        }
    }
}