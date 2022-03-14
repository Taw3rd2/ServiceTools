package com.waldorfprogramming.servicetools3.customers.customer_repository

import com.google.firebase.firestore.FirebaseFirestore
import com.waldorfprogramming.servicetools3.adapters.OnError
import com.waldorfprogramming.servicetools3.adapters.OnSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class CustomerRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun getAllCustomersFromFirestore() = callbackFlow {

        val collection = firestore
            .collection("customers")
            .orderBy("lastname")

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