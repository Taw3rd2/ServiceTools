package com.waldorfprogramming.servicetools3.daily_service.dispatch_create.work_list_drop_down

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waldorfprogramming.servicetools3.adapters.FirestoreDataResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class WorkListDropDownViewModel(
    private val repository: WorkListDropDownRepository
): ViewModel() {

    val workListStateFlow = MutableStateFlow<FirestoreDataResponse?>(null)

    init {
        viewModelScope.launch {
            repository.getWorkListFromFirestore()
                .collect {
                    workListStateFlow.value = it
                }
        }
    }
}