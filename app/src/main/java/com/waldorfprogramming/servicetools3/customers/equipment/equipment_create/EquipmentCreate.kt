package com.waldorfprogramming.servicetools3.customers.equipment.equipment_create

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
class EquipmentCreate: ComponentActivity() {

    private var customerId: String = ""
    private var customerFirstName: String = ""
    private var customerLastName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        if (bundle != null) {
            customerId = bundle.getString("CustomerId").toString()
            customerFirstName = bundle.getString("CustomerFirstName").toString()
            customerLastName = bundle.getString("CustomerLastName").toString()
        }

        val activityKiller: () -> Unit = {
            this.finish()
        }

        setContent {
            ServiceTools3Theme {
                EquipmentCreationScreen(
                    customerId,
                    customerFirstName,
                    customerLastName,
                    activityKiller
                )
            }
        }
    }
}