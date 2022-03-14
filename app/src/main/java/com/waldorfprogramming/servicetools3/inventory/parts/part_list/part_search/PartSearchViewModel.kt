package com.waldorfprogramming.servicetools3.inventory.parts.part_list.part_search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waldorfprogramming.servicetools3.adapters.DataOrException
import com.waldorfprogramming.servicetools3.inventory.parts.PartModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class PartSearchViewModel (
    val repository: PartSearchRepository
        ): ViewModel() {
            val data: MutableState<DataOrException<List<PartModel>, Boolean, Exception>>
                = mutableStateOf(
                DataOrException(listOf(), true, Exception(""))
            )

    private val searchText: MutableStateFlow<String> = MutableStateFlow("")
    private var showProgressBar: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var matchedParts: MutableStateFlow<List<PartModel>> =
        MutableStateFlow(arrayListOf())

    val partSearchModelState = combine(
        searchText,
        matchedParts,
        showProgressBar
    ) { text, matchedParts, showProgress ->
        PartSearchModelState(
            text,
            matchedParts,
            showProgress
        )
    }

    init {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllPartsFromFirebase()
            if(!data.value.data.isNullOrEmpty()) data.value.loading = false
        }
    }

    fun onSearchTextChanged(changedSearchText: String) {
        searchText.value = changedSearchText
        if(changedSearchText.isEmpty()){
            matchedParts.value = arrayListOf()
            return
        }

        val partsFromSearch = data.value.data!!.filter { x ->
            x.partNumber.contains(changedSearchText, true) ||
                    x.partDescription.contains(changedSearchText, true)
        }

        matchedParts.value = partsFromSearch
    }

    fun onClearClick() {
        searchText.value = ""
        matchedParts.value = arrayListOf()
    }


}