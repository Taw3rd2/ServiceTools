package com.waldorfprogramming.servicetools3.inventory.parts.parts_repository

import com.google.firebase.firestore.FirebaseFirestore
import com.waldorfprogramming.servicetools3.adapters.OnError
import com.waldorfprogramming.servicetools3.adapters.OnSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class PartsRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun getAllPartsFromFirestore() = callbackFlow {

        val collection = firestore
            .collection("parts")
            .orderBy("partNumber")

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