package com.waldorfprogramming.servicetools3.inventory.navigation_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waldorfprogramming.servicetools3.adapters.FirestoreDataResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class InventoryNavigationViewModel (
    val repository: InventoryNavigationRepository
        ): ViewModel() {

    val containerStateFlow = MutableStateFlow<FirestoreDataResponse?>(null)

    init {
        viewModelScope.launch {
            repository.getAllContainersFromFirestore().collect {
                containerStateFlow.value = it
            }
        }
    }
}