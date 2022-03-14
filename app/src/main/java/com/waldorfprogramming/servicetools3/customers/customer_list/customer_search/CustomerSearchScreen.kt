package com.waldorfprogramming.servicetools3.customers.customer_list.customer_search

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import com.waldorfprogramming.servicetools3.adapters.rememberFlowWithLifecycle
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun CustomerSearchScreen(
    customerSearchViewModel: CustomerSearchViewModel
) {
    val customerSearchModelState by rememberFlowWithLifecycle(
        customerSearchViewModel.customerSearchModelState).collectAsState(
        initial = CustomerSearchModelState.Empty)

    SearchBarUI(
        searchText = customerSearchModelState.searchText,
        placeholderText = "Search Customers",
        onSearchTextChanged = { customerSearchViewModel.onSearchTextChanged(it)},
        onClearClick = { customerSearchViewModel.onClearClick() },
        matchesFound = customerSearchModelState.customers.isNotEmpty()
    ){
        Surface(color = MaterialTheme.colors.background) {
            ListOfSearchedCustomers(
                customers = customerSearchModelState.customers,
                isLoading = false
            )
        }
    }
}