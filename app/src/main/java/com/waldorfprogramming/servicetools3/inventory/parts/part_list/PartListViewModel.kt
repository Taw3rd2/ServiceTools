package com.waldorfprogramming.servicetools3.inventory.parts.part_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waldorfprogramming.servicetools3.adapters.FirestoreDataResponse
import com.waldorfprogramming.servicetools3.inventory.parts.parts_repository.PartsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class PartListViewModel (
    val repository: PartsRepository
        ): ViewModel() {

    val partsStateFlow = MutableStateFlow<FirestoreDataResponse?>(null)

    init {
        viewModelScope.launch {
            repository.getAllPartsFromFirestore().collect {
                partsStateFlow.value = it
            }
        }
    }
}