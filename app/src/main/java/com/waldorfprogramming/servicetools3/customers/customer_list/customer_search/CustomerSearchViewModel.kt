package com.waldorfprogramming.servicetools3.customers.customer_list.customer_search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waldorfprogramming.servicetools3.adapters.DataOrException
import com.waldorfprogramming.servicetools3.customers.CustomerModel
import com.waldorfprogramming.servicetools3.customers.customer_repository.CustomerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class CustomerSearchViewModel(
    val repository: CustomerSearchRepository
): ViewModel() {

    val data: MutableState<DataOrException<List<CustomerModel>, Boolean, Exception>>
        = mutableStateOf(
            DataOrException(listOf(), true, Exception(""))
        )

    private val searchText: MutableStateFlow<String> = MutableStateFlow("")
    private var showProgressBar: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var matchedCustomers: MutableStateFlow<List<CustomerModel>> =
        MutableStateFlow(arrayListOf())

    val customerSearchModelState = combine(
        searchText,
        matchedCustomers,
        showProgressBar
    ) { text, matchedCustomers, showProgress ->
        CustomerSearchModelState(
            text,
            matchedCustomers,
            showProgress
        )
    }

    init {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllCustomersFromFirebase()
            if(!data.value.data.isNullOrEmpty()) data.value.loading = false
        }
    }

    fun onSearchTextChanged(changedSearchText: String) {
        searchText.value = changedSearchText
        if(changedSearchText.isEmpty()){
            matchedCustomers.value = arrayListOf()
            return
        }

        val customersFromSearch = data.value.data!!.filter { x ->
            x.lastname.contains(changedSearchText, true) ||
                    x.street.contains(changedSearchText, true)
        }

        matchedCustomers.value = customersFromSearch
    }

    fun onClearClick() {
        searchText.value = ""
        matchedCustomers.value = arrayListOf()
    }
}