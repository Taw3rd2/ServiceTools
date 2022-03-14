package com.waldorfprogramming.servicetools3.customers.equipment.equipment_repository

import com.google.firebase.firestore.FirebaseFirestore
import com.waldorfprogramming.servicetools3.adapters.OnError
import com.waldorfprogramming.servicetools3.adapters.OnSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class EquipmentRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun getAllEquipmentFromFirestore(customerId: String) = callbackFlow {

        val collection = firestore
            .collection("customers")
            .document(customerId)
            .collection("Equipment")

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