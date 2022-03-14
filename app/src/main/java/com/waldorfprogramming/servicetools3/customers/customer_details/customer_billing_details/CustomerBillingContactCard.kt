package com.waldorfprogramming.servicetools3.customers.customer_details.customer_billing_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp

@Composable
fun CustomerBillingContactCard (
    contactCardName: String,
    billingName: String,
    billingPhone: String,
    billingEmail: String
        ){
    if(
    !billingName.isNullOrEmpty() ||
    !billingPhone.isNullOrEmpty() ||
    !billingEmail.isNullOrEmpty()
    ){
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = 5.dp,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(color = MaterialTheme.colors.background)
            ) {
                Text(
                    text = contactCardName,
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic
                )
                if (!billingName.isNullOrEmpty()) {
                    Text(
                        text = billingName,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.primary
                    )
                }
                if (!billingPhone.isNullOrEmpty()) {
                    Text(
                        text = billingPhone,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.primary
                    )
                }
                if (!billingEmail.isNullOrEmpty()) {
                    Text(
                        text = billingEmail,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}