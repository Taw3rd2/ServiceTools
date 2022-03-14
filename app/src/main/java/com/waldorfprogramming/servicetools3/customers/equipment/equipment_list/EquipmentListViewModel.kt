package com.waldorfprogramming.servicetools3.customers.equipment.equipment_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waldorfprogramming.servicetools3.adapters.FirestoreDataResponse
import com.waldorfprogramming.servicetools3.customers.equipment.equipment_repository.EquipmentRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class EquipmentListViewModel (
    private val repository: EquipmentRepository,
    val customerId: String,
    val customerFirstName: String,
    val customerLastName: String
    ): ViewModel() {

    val firstName: String = customerFirstName
    val lastName: String = customerLastName

    val equipmentStateFlow = MutableStateFlow<FirestoreDataResponse?>(null)

    init {
        viewModelScope.launch {
            repository.getAllEquipmentFromFirestore(customerId).collect {
                equipmentStateFlow.value = it
            }
        }
    }
}