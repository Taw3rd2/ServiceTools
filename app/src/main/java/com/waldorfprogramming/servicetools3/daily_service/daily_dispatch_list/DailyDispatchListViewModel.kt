package com.waldorfprogramming.servicetools3.daily_service.daily_dispatch_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waldorfprogramming.servicetools3.adapters.FirestoreDataResponse
import com.waldorfprogramming.servicetools3.daily_service.repository.DispatchRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalCoroutinesApi
class DailyDispatchListViewModel(
    private val repository: DispatchRepository,
    techLead: String,
): ViewModel() {

    val dailyDispatchStateFlow = MutableStateFlow<FirestoreDataResponse?>(null)

    init {
        viewModelScope.launch {
            repository.getAllDispatchesFromFirestore(
                techLead,
                getStartTime(),
                getEndTime()
            ).collect {
                dailyDispatchStateFlow.value = it
            }
        }
    }

    private fun getStartTime(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        return cal.timeInMillis /1000
    }

    private fun getEndTime(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 23)
        cal.set(Calendar.MINUTE, 59)
        cal.set(Calendar.SECOND, 59)
        return cal.timeInMillis / 1000
    }
}