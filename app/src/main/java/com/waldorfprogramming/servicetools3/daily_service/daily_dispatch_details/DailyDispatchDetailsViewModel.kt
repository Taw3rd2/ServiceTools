package com.waldorfprogramming.servicetools3.daily_service.daily_dispatch_details

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.waldorfprogramming.servicetools3.customers.CustomerModel
import com.waldorfprogramming.servicetools3.daily_service.DispatchModel

class DailyDispatchDetailsViewModel(
    handle: SavedStateHandle
): ViewModel() {

    private val dispatchId: String = handle.get<String>("DispatchId").toString()

    private val _dispatchState = mutableStateOf(DispatchModel())
    var dispatchState: MutableState<DispatchModel> = _dispatchState

    init {
        subscribeToDispatchUpdates(
            dispatchId
        )
    }

    private fun subscribeToDispatchUpdates(
        dispatchId: String
    ){
        val db = FirebaseFirestore.getInstance()
        val dispatchDocumentReference = db
            .collection("events")
            .document(dispatchId)
        dispatchDocumentReference.addSnapshotListener { dispatchDocument, error ->
            error?.let {
                Log.d("FB", "subscribeToDispatchUpdates: ERROR! ${it.message}")
                return@addSnapshotListener
            }
            dispatchDocument?.let {
                val fetchedDispatch = dispatchDocument.toObject(DispatchModel::class.java)
                if (fetchedDispatch != null) {
                    _dispatchState.value = fetchedDispatch
                }
            }
        }
    }
}