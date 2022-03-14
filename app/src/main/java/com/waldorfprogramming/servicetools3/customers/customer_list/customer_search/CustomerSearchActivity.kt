package com.waldorfprogramming.servicetools3.customers.customer_list.customer_search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModelProvider
import com.waldorfprogramming.servicetools3.adapters.createWithFactory
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
class CustomerSearchActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val model: CustomerSearchViewModel by lazy {
            ViewModelProvider(
                this,
                createWithFactory {
                    CustomerSearchViewModel(repository = CustomerSearchRepository())
                }
            ).get(CustomerSearchViewModel::class.java)
        }

        setContent {
            ServiceTools3Theme {
                CustomerSearchScreen(model)
            }
        }
    }
}