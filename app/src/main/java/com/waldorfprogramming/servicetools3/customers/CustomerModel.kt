package com.waldorfprogramming.servicetools3.customers

import com.google.firebase.firestore.DocumentId

data class CustomerModel(
    @DocumentId
    val id: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val street: String = "",
    val city: String = "",
    val state: String = "",
    val zip: String = "",
    val phoneName: String = "",
    val phone: String = "",
    val altPhoneName: String = "",
    val altphone: String = "",
    val otherPhoneName: String = "",
    val otherPhone: String = "",
    val squarefootage: String = "",

    val billingorg: String = "",
    val billingPrimaryName: String = "",
    val billingPrimaryPhone: String = "",
    val billingPrimaryEmail: String = "",
    val billingAlternateName: String = "",
    val billingAlternatePhone: String = "",
    val billingAlternateEmail: String = "",
    val billingOtherName: String = "",
    val billingOtherPhone: String = "",
    val billingOtherEmail: String = "",
    val billingstreet: String = "",
    val billingcity: String = "",
    val billingstate: String = "",
    val billingzip: String = "",
    val cnotes: String = "",
    val iscommercial: Boolean = false,
    val noService: Boolean = false,
)
