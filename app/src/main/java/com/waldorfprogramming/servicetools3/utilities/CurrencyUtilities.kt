package com.waldorfprogramming.servicetools3.utilities

import java.text.NumberFormat
import java.util.*

fun longToStringCurrency(centsInput: Long): String {
    val centsToDouble = centsInput.toDouble()
    val centsReduced = centsToDouble / 100
    val test = NumberFormat.getCurrencyInstance(Locale.US)
    test.minimumFractionDigits
    test.maximumFractionDigits
    return test.format(centsReduced)
}

fun longToStringCurrencyWithMarkup(centsInput: Long, markUp: Double): String{
    val centsToDouble = centsInput.toDouble()
    val centsReduced = centsToDouble / 100
    val centsMarkedUp = centsReduced * markUp
    val test = NumberFormat.getCurrencyInstance(Locale.US)
    test.minimumFractionDigits
    test.maximumFractionDigits
    return test.format(centsMarkedUp)
}