package com.waldorfprogramming.servicetools3.inventory.parts

import com.google.firebase.firestore.DocumentId

data class PartModel(
    @DocumentId
    var partId: String = "",
    var partNumber: String = "",
    var partDescription: String = "",
    var partCost: Long = 0,
    var partLabor: Double = 0.0,
    var partVendor: String = "",
    var partDataDate: String = "",
    var partDataServicer: String = "",
    var partNotes: String = "",
    var category: String = "",
    var url: String = ""
)
