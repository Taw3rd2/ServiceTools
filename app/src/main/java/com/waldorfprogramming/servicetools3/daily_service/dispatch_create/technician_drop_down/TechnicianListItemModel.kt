package com.waldorfprogramming.servicetools3.daily_service.dispatch_create.technician_drop_down

import com.google.firebase.firestore.DocumentId

data class TechnicianListItemModel(
    @DocumentId
    val technicianListItemId: String = "",
    val color: String = "",
    val email: String = "",
    val name: String = ""
)
