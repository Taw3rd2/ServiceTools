package com.waldorfprogramming.servicetools3.customers.customer_list.customer_search

import com.waldorfprogramming.servicetools3.customers.CustomerModel

data class CustomerSearchModelState(
    val searchText: String = "",
    val customers: List<CustomerModel> = arrayListOf(),
    val showProgressBar: Boolean = false
) {
    companion object {
        val Empty = CustomerSearchModelState()
    }
}
