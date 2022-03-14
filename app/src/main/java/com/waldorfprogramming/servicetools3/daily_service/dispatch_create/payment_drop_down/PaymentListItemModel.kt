package com.waldorfprogramming.servicetools3.daily_service.dispatch_create.payment_drop_down

import com.google.firebase.firestore.DocumentId

data class PaymentListItemModel(
    @DocumentId
    val paymentListItemId: String = "",
    val item: String = ""
)
