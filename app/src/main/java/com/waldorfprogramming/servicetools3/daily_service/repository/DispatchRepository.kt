package com.waldorfprogramming.servicetools3.daily_service.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.waldorfprogramming.servicetools3.adapters.OnError
import com.waldorfprogramming.servicetools3.adapters.OnSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class DispatchRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun getAllDispatchesFromFirestore(
        techLead: String,
        dayStart: Long,
        dayEnd: Long
    )  = callbackFlow {

        val collection = firestore
            .collection("events")
            .whereGreaterThanOrEqualTo("scheduledDate", dayStart)
            .whereLessThan("scheduledDate", dayEnd)
            .whereEqualTo("techLead", techLead)

        val snapshotListener = collection.addSnapshotListener { value, error ->
            val response = if (error == null) {
                OnSuccess(value)
            } else {
                OnError(error)
            }
            this.trySend(response).isSuccess
        }

        awaitClose {
            snapshotListener.remove()
        }
    }
}