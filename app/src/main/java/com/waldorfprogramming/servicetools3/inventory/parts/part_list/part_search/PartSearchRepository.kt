package com.waldorfprogramming.servicetools3.inventory.parts.part_list.part_search

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.waldorfprogramming.servicetools3.adapters.DataOrException
import com.waldorfprogramming.servicetools3.inventory.parts.PartModel
import kotlinx.coroutines.tasks.await

class PartSearchRepository {

    private val queryPart = FirebaseFirestore
        .getInstance()
        .collection("parts")
        .orderBy("partNumber")

    suspend fun getAllPartsFromFirebase():
            DataOrException<List<PartModel>, Boolean, Exception>{
        val dataOrException = DataOrException<List<PartModel>, Boolean, Exception>()

        try {
            dataOrException.loading = true
            dataOrException.data = queryPart
                .get()
                .await()
                .documents
                .map { documentSnapshot ->
                    documentSnapshot.toObject(PartModel::class.java)!!
                }
            if(!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false
        } catch(exception: FirebaseFirestoreException){
            dataOrException.e = exception
        }
        return dataOrException
    }
}