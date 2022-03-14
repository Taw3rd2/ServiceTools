package com.waldorfprogramming.servicetools3.customers.customer_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waldorfprogramming.servicetools3.adapters.FirestoreDataResponse
import com.waldorfprogramming.servicetools3.customers.customer_repository.CustomerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CustomerListViewModel (
    val repository: CustomerRepository
        ): ViewModel() {

    val customerStateFlow = MutableStateFlow<FirestoreDataResponse?>(null)

    init {
        viewModelScope.launch {
            repository.getAllCustomersFromFirestore().collect {
                customerStateFlow.value = it
            }
        }
    }
}