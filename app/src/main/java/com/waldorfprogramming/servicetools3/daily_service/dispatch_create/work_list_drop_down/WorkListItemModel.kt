package com.waldorfprogramming.servicetools3.daily_service.dispatch_create.work_list_drop_down

import com.google.firebase.firestore.DocumentId

data class WorkListItemModel(
    @DocumentId
    val workListItemId: String = "",
    val item: String = "",
    val shorthand: String = ""
)
