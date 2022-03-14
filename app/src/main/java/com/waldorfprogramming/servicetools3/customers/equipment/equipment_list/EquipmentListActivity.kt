package com.waldorfprogramming.servicetools3.customers.equipment.equipment_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModelProvider
import com.waldorfprogramming.servicetools3.adapters.createWithFactory
import com.waldorfprogramming.servicetools3.customers.equipment.equipment_repository.EquipmentRepository
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
class EquipmentListActivity: ComponentActivity() {

    private var customerId: String = ""
    private var customerFirstName: String = ""
    private var customerLastName: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        if (bundle != null){
            customerId = bundle.getString("customerId").toString()
            customerFirstName = bundle.getString("customerFirstName").toString()
            customerLastName = bundle.getString("customerLastName").toString()
        }

        val model: EquipmentListViewModel by lazy {
            ViewModelProvider(
                this,
                createWithFactory {
                    EquipmentListViewModel(
                        repository = EquipmentRepository(),
                        customerId,
                        customerFirstName,
                        customerLastName
                    )
                }
            ).get(EquipmentListViewModel::class.java)
        }

        setContent {
            ServiceTools3Theme {
                EquipmentListScreen(
                    model,
                    customerFirstName,
                    customerLastName
                )
            }
        }
    }
}