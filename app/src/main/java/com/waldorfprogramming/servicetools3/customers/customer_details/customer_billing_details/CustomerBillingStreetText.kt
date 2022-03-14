package com.waldorfprogramming.servicetools3.customers.customer_details.customer_billing_details

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontStyle

@Composable
fun CustomerBillingStreetText(billingStreetAddress: String) {
    if(billingStreetAddress.isNotEmpty()){
        Text(
            text = "Billing Address",
            style = MaterialTheme.typography.caption,
            fontStyle = FontStyle.Italic
        )
        Text(
            text = billingStreetAddress,
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primary
        )
    } else {
        Text(
            text = "No Billing Address Entered",
            style = MaterialTheme.typography.caption,
            fontStyle = FontStyle.Italic
        )
    }
}