package com.waldorfprogramming.servicetools3.customers.customer_details.customer_billing_details

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CustomerBillingOrganizationText( billingOrganizationName: String) {
    if(billingOrganizationName.isNotEmpty()){
        Text(
            text = "Billing Organization",
            style = MaterialTheme.typography.caption,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Start
        )
        Text(
            text = billingOrganizationName,
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primary
        )
    } else {
        Text(
            text = "No Billing Organization Entered",
            style = MaterialTheme.typography.caption,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Start
        )
    }
}