package com.waldorfprogramming.servicetools3.customers.customer_details.customer_billing_details

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.waldorfprogramming.servicetools3.components.FABEditContent
import com.waldorfprogramming.servicetools3.components.ServiceToolsTopBar
import com.waldorfprogramming.servicetools3.customers.customer_details.CustomerDetailsViewModel
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme
import com.waldorfprogramming.servicetools3.utilities.addressStringBuilder
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
class CustomerBillingDetails: ComponentActivity() {

    var customerId: String? = null
    var customerFirstname: String? = null
    var customerLastname: String? = null

    private var userName: String? = null
    private var logDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)
        if (googleSignInAccount != null) {
            userName = googleSignInAccount.displayName
        }

        val bundle = intent.extras
        if (bundle != null) {
            customerId = bundle.getString("UpdateCustomerId")
        }

        val viewModel: CustomerDetailsViewModel by viewModels()

        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("h:mm a", Locale.US)
        logDate = sdf.format(cal.time)

        setContent { 
            ServiceTools3Theme {
                Scaffold(
                    topBar = {
                        ServiceToolsTopBar(title = "Customer Billing Details")
                    },
                    floatingActionButton = {
                        FABEditContent(contentDescription = "Edit Billing Information") {
                            val updateCustomerBillingIntent =
                                Intent(this@CustomerBillingDetails, CustomerEditBillingDetails::class.java)
                            updateCustomerBillingIntent.putExtra("UpdateCustomerId", customerId)
                            startActivity(updateCustomerBillingIntent)
                        }
                    }
                ) {
                    val scrollState = rememberScrollState()
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        Column(
                            modifier = Modifier.padding(top = 40.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if(viewModel.customerState.value.billingorg.isNullOrBlank()){
                                CustomerBillingOrganizationText(
                                    ""
                                )
                            } else {
                                CustomerBillingOrganizationText(
                                    viewModel.customerState.value.billingorg
                                )
                            }

                            if(viewModel.customerState.value.billingstreet.isNullOrBlank()){
                                CustomerBillingStreetText("")
                            } else {
                                CustomerBillingStreetText(
                                    viewModel.customerState.value.billingstreet
                                )
                            }

                            val secondAddress = addressStringBuilder(
                                viewModel.customerState.value.billingcity,
                                viewModel.customerState.value.billingstate,
                                viewModel.customerState.value.billingzip
                            )
                            CustomerBillingAddressLineTwo(secondAddress)

                            CustomerBillingContactCard(
                                "Primary Contact",
                                if(!viewModel.customerState.value.billingPrimaryName.isNullOrBlank()){
                                    viewModel.customerState.value.billingPrimaryName
                                } else {""}
                                ,
                                if(!viewModel.customerState.value.billingPrimaryPhone.isNullOrBlank()){
                                    viewModel.customerState.value.billingPrimaryPhone
                                } else {""}
                                ,
                                if(!viewModel.customerState.value.billingPrimaryEmail.isNullOrBlank()) {
                                    viewModel.customerState.value.billingPrimaryEmail
                                } else {""}
                            )

                            CustomerBillingContactCard(
                                "Alternate Contact",
                                if(!viewModel.customerState.value.billingAlternateName.isNullOrBlank()){
                                    viewModel.customerState.value.billingAlternateName
                                } else {""}
                                ,
                                if(!viewModel.customerState.value.billingAlternatePhone.isNullOrBlank()){
                                    viewModel.customerState.value.billingAlternatePhone
                                } else {""}
                                ,
                                if(!viewModel.customerState.value.billingAlternateEmail.isNullOrBlank()) {
                                    viewModel.customerState.value.billingAlternateEmail
                                } else {""}
                            )

                            CustomerBillingContactCard(
                                "Other Contact",
                                if(!viewModel.customerState.value.billingOtherName.isNullOrBlank()){
                                    viewModel.customerState.value.billingOtherName
                                } else {""}
                                ,
                                if(!viewModel.customerState.value.billingOtherPhone.isNullOrBlank()){
                                    viewModel.customerState.value.billingOtherPhone
                                } else {""}
                                ,
                                if(!viewModel.customerState.value.billingOtherEmail.isNullOrBlank()) {
                                    viewModel.customerState.value.billingOtherEmail
                                } else {""}
                            )
                        }
                    }
                }
            }
        }

    }
}