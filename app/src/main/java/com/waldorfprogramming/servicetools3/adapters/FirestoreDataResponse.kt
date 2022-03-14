package com.waldorfprogramming.servicetools3.adapters

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

sealed class FirestoreDataResponse
data class OnSuccess(val querySnapshot: QuerySnapshot?): FirestoreDataResponse()
data class OnError(val exception: FirebaseFirestoreException?): FirestoreDataResponse()
