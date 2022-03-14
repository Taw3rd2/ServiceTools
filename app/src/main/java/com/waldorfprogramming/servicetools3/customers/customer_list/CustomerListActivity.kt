package com.waldorfprogramming.servicetools3.customers.customer_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModelProvider
import com.waldorfprogramming.servicetools3.adapters.createWithFactory
import com.waldorfprogramming.servicetools3.customers.customer_repository.CustomerRepository
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
class CustomerListActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val model: CustomerListViewModel by lazy {
            ViewModelProvider(
                this,
                createWithFactory {
                    CustomerListViewModel(repository = CustomerRepository())
                }
            ).get(CustomerListViewModel::class.java)
        }

        setContent {
            ServiceTools3Theme {
                CustomerListScreen(model)
            }
        }
    }
}