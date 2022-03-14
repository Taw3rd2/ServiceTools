package com.waldorfprogramming.servicetools3.customers.job_history.job_history_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waldorfprogramming.servicetools3.adapters.FirestoreDataResponse
import com.waldorfprogramming.servicetools3.customers.job_history.job_history_repository.CustomerJobHistoryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class CustomerJobHistoryListViewModel(
    private val repository: CustomerJobHistoryRepository,
    val customerId: String
): ViewModel() {

    val jobHistoryStateFlow = MutableStateFlow<FirestoreDataResponse?>(null)

    init {
        viewModelScope.launch {
            repository.getAllJobsFromFirestore(customerId).collect {
                jobHistoryStateFlow.value = it
            }
        }
    }
}