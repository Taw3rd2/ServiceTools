package com.waldorfprogramming.servicetools3.customers.customer_details.customer_billing_details

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun CustomerBillingAddressLineTwo(
    secondAddress: String
) {
    if(secondAddress.isNotEmpty()){
        Text(
            text = secondAddress,
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primary
        )
    }
}