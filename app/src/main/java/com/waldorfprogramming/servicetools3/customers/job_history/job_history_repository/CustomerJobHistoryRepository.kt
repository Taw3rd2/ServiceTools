package com.waldorfprogramming.servicetools3.customers.job_history.job_history_repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.waldorfprogramming.servicetools3.adapters.OnError
import com.waldorfprogramming.servicetools3.adapters.OnSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class CustomerJobHistoryRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun getAllJobsFromFirestore(customerId: String) = callbackFlow {

        val collection = firestore
            .collection("events")
            .whereEqualTo("customerId", customerId)

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