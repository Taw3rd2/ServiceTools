package com.waldorfprogramming.servicetools3.customers.customer_list

import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.waldorfprogramming.servicetools3.customers.CustomerModel
import com.waldorfprogramming.servicetools3.customers.customer_details.CustomerDetailsActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun ListOfCustomers( customers: List<CustomerModel>) {

    val context = LocalContext.current

    if(customers.isNullOrEmpty()){
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
                key = {it.id}
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
                    CustomerListItem(customer = it) {
                        val customerDetailsIntent =
                        Intent(context, CustomerDetailsActivity::class.java)
                        customerDetailsIntent.putExtra("UpdateCustomerId", it.id)
                        context.startActivity(customerDetailsIntent)
                    }
                }
            }
        }
    }
}