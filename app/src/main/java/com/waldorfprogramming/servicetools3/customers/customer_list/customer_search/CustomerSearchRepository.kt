package com.waldorfprogramming.servicetools3.customers.customer_list.customer_search

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.waldorfprogramming.servicetools3.adapters.DataOrException
import com.waldorfprogramming.servicetools3.customers.CustomerModel
import kotlinx.coroutines.tasks.await

class CustomerSearchRepository {
    private val queryCustomer = FirebaseFirestore
        .getInstance()
        .collection("customers")
        .orderBy("lastname")

    suspend fun getAllCustomersFromFirebase():
            DataOrException<List<CustomerModel>, Boolean, Exception>{
        val dataOrException = DataOrException<List<CustomerModel>, Boolean, Exception>()

        try {
            dataOrException.loading = true
            dataOrException.data = queryCustomer
                .get()
                .await()
                .documents
                .map { documentSnapshot ->
                    documentSnapshot.toObject(CustomerModel::class.java)!!
                }
            if(!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false
        } catch(exception: FirebaseFirestoreException) {
            dataOrException.e = exception
        }
        return dataOrException
    }
}