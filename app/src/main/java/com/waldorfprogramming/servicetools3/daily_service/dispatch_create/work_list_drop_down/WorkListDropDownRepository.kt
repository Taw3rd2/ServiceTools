package com.waldorfprogramming.servicetools3.daily_service.dispatch_create.work_list_drop_down

import com.google.firebase.firestore.FirebaseFirestore
import com.waldorfprogramming.servicetools3.adapters.OnError
import com.waldorfprogramming.servicetools3.adapters.OnSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class WorkListDropDownRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun getWorkListFromFirestore() = callbackFlow {
        val collection = firestore
            .collection("workList")
            .orderBy("item")
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