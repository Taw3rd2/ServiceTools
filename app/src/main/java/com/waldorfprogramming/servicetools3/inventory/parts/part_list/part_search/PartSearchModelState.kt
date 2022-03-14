package com.waldorfprogramming.servicetools3.inventory.parts.part_list.part_search

import com.waldorfprogramming.servicetools3.inventory.parts.PartModel

data class PartSearchModelState(
    val searchText: String = "",
    val parts: List<PartModel> = arrayListOf(),
    val showProgressBar: Boolean = false
) {
    companion object {
        val Empty = PartSearchModelState()
    }
}
