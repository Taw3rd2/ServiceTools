package com.waldorfprogramming.servicetools3.daily_service.dispatch_create.payment_drop_down

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waldorfprogramming.servicetools3.adapters.FirestoreDataResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class PaymentDropDownViewModel(
    private val repository: PaymentDropDownRepository
): ViewModel() {

    val paymentListStateFlow = MutableStateFlow<FirestoreDataResponse?>(null)

    init {
        viewModelScope.launch {
            repository.getPaymentListFromFirestore()
                .collect {
                    paymentListStateFlow.value = it
                }
        }
    }
}