package com.waldorfprogramming.servicetools3.utilities

import com.google.firebase.Timestamp
import java.util.*
import kotlin.collections.HashMap

fun createCustomerMap(
    firstname: String, lastname: String, street: String, city: String,
    state: String, zip: String, phoneName: String, phone: String, altPhoneName: String,
    altphone: String, otherPhoneName: String, otherPhone: String,
    squareFootage: String, iscommercial: Boolean, noService: Boolean
): HashMap<String, Any> {
    return hashMapOf(
        "firstname" to firstname.trim(),
        "lastname" to lastname.trim(),
        "street" to street.trim(),
        "city" to city.trim(),
        "state" to state.trim(),
        "zip" to zip.trim(),
        "phoneName" to phoneName.trim(),
        "phone" to phone.trim(),
        "altPhoneName" to altPhoneName.trim(),
        "altphone" to altphone.trim(),
        "otherPhoneName" to otherPhoneName.trim(),
        "otherPhone" to otherPhone.trim(),
        "squarefootage" to squareFootage.trim(),
        "iscommercial" to iscommercial,
        "noService" to noService
    )
}

fun createCustomerEquipmentMap(
    customerId: String,
    equipmentBrand: String,
    equipmentBtu: String,
    equipmentContract: String,
    equipmentEff: String,
    equipmentFuel: String,
    equipmentModel: String,
    equipmentName: String,
    equipmentNotes: String,
    equipmentSerial: String,
    equipmentVoltage: String,
    equipmentWarranty: String,
    key: String,
    laborWarranty: String,
): HashMap<String, Any>{
    return hashMapOf(
        "customerId" to customerId,
        "equipmentBrand" to equipmentBrand.trim(),
        "equipmentBtu" to equipmentBtu.trim(),
        "equipmentContract" to equipmentContract.trim(),
        "equipmentEff" to equipmentEff.trim(),
        "equipmentFuel" to equipmentFuel.trim(),
        "equipmentModel" to equipmentModel.trim(),
        "equipmentName" to equipmentName.trim(),
        "equipmentNotes" to equipmentNotes.trim(),
        "equipmentSerial" to equipmentSerial.trim(),
        "equipmentVoltage" to equipmentVoltage.trim(),
        "equipmentWarranty" to equipmentWarranty.trim(),
        "key" to key.trim(),
        "laborWarranty" to laborWarranty.trim(),
    )
}

fun createCustomerEquipmentUpdateMap(
    customerId: String,
    equipmentBrand: String,
    equipmentBtu: String,
    equipmentEff: String,
    equipmentFuel: String,
    equipmentModel: String,
    equipmentName: String,
    equipmentSerial: String,
    equipmentVoltage: String,
    key: String,
): HashMap<String, Any>{
    return hashMapOf(
        "customerId" to customerId,
        "equipmentBrand" to equipmentBrand.trim(),
        "equipmentBtu" to equipmentBtu.trim(),
        "equipmentEff" to equipmentEff.trim(),
        "equipmentFuel" to equipmentFuel.trim(),
        "equipmentModel" to equipmentModel.trim(),
        "equipmentName" to equipmentName.trim(),
        "equipmentSerial" to equipmentSerial.trim(),
        "equipmentVoltage" to equipmentVoltage.trim(),
        "key" to key.trim(),
    )
}

fun createCustomerBillingMap(
    billingOrg: String,
    billingPrimaryName: String,
    billingPrimaryPhone: String,
    billingPrimaryEmail: String,
    billingAlternateName: String,
    billingAlternatePhone: String,
    billingAlternateEmail: String,
    billingOtherName: String,
    billingOtherPhone: String,
    billingOtherEmail: String,
    billingStreet: String,
    billingCity: String,
    billingState: String,
    billingZip: String,
    iscommercial: Boolean,
    noService: Boolean
): HashMap<String, Any> {
    return hashMapOf(
        "billingorg" to billingOrg.trim(),

        "billingPrimaryName" to billingPrimaryName.trim(),
        "billingPrimaryPhone" to billingPrimaryPhone.trim(),
        "billingPrimaryEmail" to billingPrimaryEmail.trim(),

        "billingAlternateName" to billingAlternateName.trim(),
        "billingAlternatePhone" to billingAlternatePhone.trim(),
        "billingAlternateEmail" to billingAlternateEmail.trim(),

        "billingOtherName" to billingOtherName.trim(),
        "billingOtherPhone" to billingOtherPhone.trim(),
        "billingOtherEmail" to billingOtherEmail.trim(),

        "billingstreet" to billingStreet.trim(),
        "billingcity" to billingCity.trim(),
        "billingstate" to billingState.trim(),
        "billingzip" to billingZip.trim(),
        "iscommercial" to iscommercial,
        "noService" to noService
    )
}
