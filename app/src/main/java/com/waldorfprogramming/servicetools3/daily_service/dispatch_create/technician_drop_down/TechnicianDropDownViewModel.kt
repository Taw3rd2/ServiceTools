package com.waldorfprogramming.servicetools3.daily_service.dispatch_create.technician_drop_down

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waldorfprogramming.servicetools3.adapters.FirestoreDataResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class TechnicianDropDownViewModel(
    private val repository: TechnicianDropDownRepository
): ViewModel() {

    val techListStateFlow = MutableStateFlow<FirestoreDataResponse?>(null)

    init {
        viewModelScope.launch {
            repository.getTechListFromFirestore()
                .collect {
                    techListStateFlow.value = it
                }
        }
    }
}