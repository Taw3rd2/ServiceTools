package com.waldorfprogramming.servicetools3.daily_service

import com.google.firebase.firestore.DocumentId
import java.util.*

data class DispatchModel(
    @DocumentId
    val dispatchId: String = "",
    val customerId: String = "",
    val altPhoneName: String = "",
    val altphone: String = "",
    val city: String = "",
    val dateCreated: Date = Date(),
    val dateModified: Date = Date(),
    val dateScheduled: Date = Date(),
    val scheduledDate: Long = 0,
    val end: Date = Date(),
    val firstname: String = "",
    val issue: String = "",
    val lastname: String = "",
    val leadSource: String = "",
    val notes: String = "",
    val payment: String = "",
    val phoneName: String = "",
    val phone: String = "",
    val start: Date = Date(),
    val street: String = "",
    val takenBy: String = "",
    val techHelper: String = "",
    val techLead: String = "",
    val timeAlotted: String = "",
    val timeOfDay: String = "",
    val title: String = "",
    val status: String = "",
    val jobNumber: String = ""
)
