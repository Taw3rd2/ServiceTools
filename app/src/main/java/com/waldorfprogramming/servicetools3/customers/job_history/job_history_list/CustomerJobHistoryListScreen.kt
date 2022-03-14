package com.waldorfprogramming.servicetools3.customers.job_history.job_history_list

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.waldorfprogramming.servicetools3.adapters.OnError
import com.waldorfprogramming.servicetools3.adapters.OnSuccess
import com.waldorfprogramming.servicetools3.components.ServiceToolsTopBar
import com.waldorfprogramming.servicetools3.daily_service.DispatchModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asStateFlow

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@Composable
fun CustomerJobHistoryListScreen(
    viewModel: CustomerJobHistoryListViewModel
) {
    val context = LocalContext.current
    val customerId = viewModel.customerId

    when (val jobHistoryList = viewModel.jobHistoryStateFlow.asStateFlow().collectAsState().value) {


        is OnError -> {
            Log.d("firebase", "EquipmentListScreen: some thing went wrong")
            Text(text = "try again when you have a better signal")
        }
        is OnSuccess -> {
            val listOfJobs = jobHistoryList.querySnapshot?.toObjects(DispatchModel::class.java)
            listOfJobs?.let {
                Scaffold(
                    topBar = {
                        ServiceToolsTopBar(title = "Job History")
                    }
                ) {
                    ListOfJobHistory(
                        customerId,
                        listOfJobs
                    )
                }
            }
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}