package com.waldorfprogramming.servicetools3.customers.customer_details

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.waldorfprogramming.servicetools3.customers.CustomerModel

class CustomerDetailsViewModel (
    handle: SavedStateHandle
        ): ViewModel() {
    private val customerId: String = handle.get<String>("UpdateCustomerId").toString()

    private val _customerState = mutableStateOf(CustomerModel())
    var customerState: MutableState<CustomerModel> = _customerState

    init {
        subscribeToCustomerUpdates(customerId)
    }

    private fun subscribeToCustomerUpdates(customerId: String) {
        val db = FirebaseFirestore.getInstance()
        val customerDocumentReference = db
            .collection("customers")
            .document(customerId)
        customerDocumentReference.addSnapshotListener { customerDocument, error ->
            error?.let {
                Log.d("FB", "subscribeToRealtimeCustomerUpdates: Some Ting Wong ${it.message}")
                return@addSnapshotListener
            }
            customerDocument?.let {
                val fetchedCustomer = customerDocument.toObject(CustomerModel::class.java)
                if (fetchedCustomer != null) {
                    _customerState.value = fetchedCustomer
                }
            }
        }
    }
}