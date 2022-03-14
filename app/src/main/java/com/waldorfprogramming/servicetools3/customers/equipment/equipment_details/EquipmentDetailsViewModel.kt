package com.waldorfprogramming.servicetools3.customers.equipment.equipment_details

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.waldorfprogramming.servicetools3.R
import com.waldorfprogramming.servicetools3.customers.equipment.EquipmentModel

class EquipmentDetailsViewModel(
    handle: SavedStateHandle
): ViewModel() {

    private val customerId: String = handle.get<String>("CustomerId").toString()
    private val equipmentId: String = handle.get<String>("EquipmentId").toString()

    private val _equipmentState = mutableStateOf(EquipmentModel())
    var equipmentState: MutableState<EquipmentModel> = _equipmentState

    init {
        subscribeToEquipmentUpdates(
            customerId, equipmentId
        )
    }

    private fun subscribeToEquipmentUpdates(
        customerId: String,
        equipmentId: String
    ){
        val db = FirebaseFirestore.getInstance()
        val equipmentDocumentReference = db
            .collection("customers")
            .document(customerId)
            .collection("Equipment")
            .document(equipmentId)
        equipmentDocumentReference.addSnapshotListener { equipmentDocument, error ->
            error?.let {
                Log.d("FB", "subscribeToEquipmentUpdates: ERROR! ${it.message}")
                return@addSnapshotListener
            }
            equipmentDocument?.let {
                val fetchedEquipment = equipmentDocument.toObject(EquipmentModel::class.java)
                if (fetchedEquipment != null) {
                    _equipmentState.value = fetchedEquipment
                }
            }
        }
    }

    fun getEquipmentImageLogo(equipmentBrand: String): Int {

        when (equipmentBrand) {
            "Carrier" -> {
                return R.drawable.carrier_logo
            }
            "Lennox" -> {
                return R.drawable.lennox_logo
            }
            "Trane" -> {
                return R.drawable.trane_logo
            }
            "Goodman" -> {
                return R.drawable.goodman_logo
            }
            "Payne" -> {
                return R.drawable.payne_logo
            }
            "Nordyne" -> {
                return R.drawable.nordyne_logo
            }
            "Coleman" -> {
                return R.drawable.coleman_logo
            }
            "Miller" -> {
                return R.drawable.miller_logo
            }
            "Amana" -> {
                return R.drawable.amana_logo
            }
            "Bryant" -> {
                return R.drawable.bryant_logo
            }
            "Rheem" -> {
                return R.drawable.rheem_logo
            }
            "Rudd" -> {
                return R.drawable.rudd_logo
            }
            "York" -> {
                return R.drawable.york_logo
            }
            "Olsen" -> {
                return R.drawable.olsen_logo
            }
            "Tappan" -> {
                return R.drawable.tappan_logo
            }
            "Weil McLain" -> {
                return R.drawable.weil_mclain_logo
            }
            "Janitrol" -> {
                return R.drawable.janitrol_logo
            }
            "Armstrong" -> {
                return R.drawable.armstrong_air_logo
            }
            "AireFlo" -> {
                return R.drawable.aire_flo_logo
            }
            "ReVerberRay" -> {
                return R.drawable.reverberay_logo
            }
            "Re-Verber-Ray" -> {
                return R.drawable.reverberay_logo
            }
            "Trion" -> {
                return R.drawable.trion_logo
            }
            "Arctic Air" -> {
                return R.drawable.arctic_air_logo
            }
            "Intertherm" -> {
                return R.drawable.intertherm_logo
            }
            else -> {
                return 3131
            }

        }

    }
}