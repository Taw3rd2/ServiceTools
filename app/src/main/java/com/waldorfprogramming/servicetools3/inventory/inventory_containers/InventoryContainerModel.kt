package com.waldorfprogramming.servicetools3.inventory.inventory_containers

import com.google.firebase.Timestamp
import com.waldorfprogramming.servicetools3.inventory.parts.PartModel

data class InventoryContainerModel(
    var containerId: String = "",
    var containerName: String = "",
    var lastInventoried: Timestamp? = null,
    var partsList: List<PartModel> = listOf(),
    var partsNeeded: List<PartModel> = listOf(),
)
