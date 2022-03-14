package com.waldorfprogramming.servicetools3.customers.job_history.job_history_list

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModelProvider
import com.waldorfprogramming.servicetools3.adapters.createWithFactory
import com.waldorfprogramming.servicetools3.customers.job_history.job_history_repository.CustomerJobHistoryRepository
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
class CustomerJobHistoryListActivity: ComponentActivity() {

    private var customerId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        if (bundle != null){
            customerId = bundle.getString("CustomerId").toString()
        }

        val jobHistoryModel: CustomerJobHistoryListViewModel by lazy {
            ViewModelProvider(
                this,
                createWithFactory {
                    CustomerJobHistoryListViewModel(
                        repository = CustomerJobHistoryRepository(),
                        customerId
                    )
                }
            ).get(CustomerJobHistoryListViewModel::class.java)
        }

        setContent {
            ServiceTools3Theme {
                CustomerJobHistoryListScreen(jobHistoryModel)
            }
        }
    }
}