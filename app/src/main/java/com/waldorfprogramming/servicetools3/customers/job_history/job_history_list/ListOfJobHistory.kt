package com.waldorfprogramming.servicetools3.customers.job_history.job_history_list

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
import com.waldorfprogramming.servicetools3.daily_service.DispatchModel
import com.waldorfprogramming.servicetools3.daily_service.daily_dispatch_details.DailyDispatchDetailsActivity

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ListOfJobHistory(
    customerId: String,
    jobHistory: List<DispatchModel>
) {
    val context = LocalContext.current

    LazyColumn{
        items(
            items = jobHistory.sortedWith(compareByDescending { it.scheduledDate }),
            key = { it.dispatchId }
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
                CustomerJobHistoryListItem(it) {
                    val dispatchDetailsIntent =
                        Intent(context, DailyDispatchDetailsActivity::class.java)
                    dispatchDetailsIntent.putExtra("DispatchId", it.dispatchId)
                    context.startActivity(dispatchDetailsIntent)
                }
            }
        }
    }
}