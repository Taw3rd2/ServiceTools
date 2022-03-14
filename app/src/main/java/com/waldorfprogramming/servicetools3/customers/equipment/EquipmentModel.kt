package com.waldorfprogramming.servicetools3.customers.equipment

import com.google.firebase.firestore.DocumentId

data class EquipmentModel(
    @DocumentId
    val id: String = "",
    val customerId: String = "",
    val equipmentBrand: String = "",
    val equipmentBtu: String = "",
    val equipmentContract: String = "",
    val equipmentEff: String = "",
    val equipmentFuel: String = "",
    val equipmentModel: String = "",
    val equipmentName: String = "",
    val equipmentNotes: String = "",
    val equipmentSerial: String = "",
    val equipmentVoltage: String = "",
    val equipmentWarranty: String = "",
    val key: String = "",
    val laborWarranty: String = "",
    val warranty: HashMap<String, Any> = HashMap()
)
