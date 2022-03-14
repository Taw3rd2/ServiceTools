package com.waldorfprogramming.servicetools3.customers.customer_list.customer_search

import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.waldorfprogramming.servicetools3.customers.CustomerModel
import com.waldorfprogramming.servicetools3.customers.customer_details.CustomerDetailsActivity
import com.waldorfprogramming.servicetools3.customers.customer_list.CustomerListItem
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun ListOfSearchedCustomers(
    customers: List<CustomerModel>,
    isLoading: Boolean
) {
    val context = LocalContext.current

    if(isLoading!!){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn{
            items(
                items = customers,
                key = { it.id }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItemPlacement(
                            animationSpec = tween(
                                durationMillis = 500
                            )
                        )
                ) {
                    CustomerListItem(it){
                        val customerDetailsIntent =
                            Intent(context, CustomerDetailsActivity::class.java)
                            customerDetailsIntent.putExtra("UpdateCustomerId", it.id)
                        context.startActivity(customerDetailsIntent)
                }
                }
            }
        }
//        LazyColumn{
//            itemsIndexed(customers) { index, customer ->
//                CustomerListItem(customer){
//                    val customerDetailsIntent =
//                        Intent(context, CustomerDetailsActivity::class.java)
//                    customerDetailsIntent.putExtra("UpdateCustomerId", customer.id)
//                    context.startActivity(customerDetailsIntent)
//                }
//            }
//        }
    }
}