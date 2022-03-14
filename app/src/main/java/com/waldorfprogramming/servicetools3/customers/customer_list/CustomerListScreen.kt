package com.waldorfprogramming.servicetools3.customers.customer_list

import android.content.Intent
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
import com.waldorfprogramming.servicetools3.components.FABAddContent
import com.waldorfprogramming.servicetools3.customers.CustomerModel
import com.waldorfprogramming.servicetools3.customers.customer_create.CustomerCreate
import com.waldorfprogramming.servicetools3.customers.customer_list.customer_search.CustomerSearchActivity
import kotlinx.coroutines.flow.asStateFlow

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun CustomerListScreen(viewModel: CustomerListViewModel) {

    val context = LocalContext.current

    when (val customerList = viewModel.customerStateFlow.asStateFlow().collectAsState().value) {

        is OnError -> {
            Log.d("firebase", "EquipmentListScreen: some thing went wrong")
            Text(text = "try again when you have a better signal")
        }
        is OnSuccess -> {
            val listOfCustomers = customerList.querySnapshot?.toObjects(CustomerModel::class.java)
            listOfCustomers?.let {
                Scaffold(
                    topBar = {
                        CustomerListTopBar( onSearchBarClick = {
                            context.startActivity(Intent(context, CustomerSearchActivity::class.java))
                        }, listOfCustomers.size )
                    },
                    floatingActionButton = {
                        FABAddContent(
                            contentDescription = "Add New Customer") {
                            context.startActivity(Intent(context, CustomerCreate::class.java))
                        }
                    }) {
                    ListOfCustomers(
                        customers = listOfCustomers
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