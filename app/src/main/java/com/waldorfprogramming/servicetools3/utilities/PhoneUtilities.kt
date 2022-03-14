package com.waldorfprogramming.servicetools3.utilities

fun removeNonNumbersFromString(phoneNumber: String): String {
    return phoneNumber.replace(("[^\\w\\d ]").toRegex(), "")
}