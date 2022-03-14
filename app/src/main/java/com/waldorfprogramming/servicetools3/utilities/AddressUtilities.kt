package com.waldorfprogramming.servicetools3.utilities

fun addressStringBuilder(city: String?, state: String?, zip: String?): String{
    var secondAddress = ""

    if (city != null && city != "") { //have city
        secondAddress = city
    }

    if (state != null && state != "") { // have a state
        secondAddress = if (city != null && city != "") { //have city
            val secondAddressState = "$secondAddress,"
            secondAddressState + state
        } else {
            state
        }
    }

    if (zip != null && zip != "") { // have zip
        if (city != null && city != "") { //have city
            secondAddress = if (state != null && state != "") { //have state
                val secondAddressZip = "$secondAddress "
                secondAddressZip + zip
            } else { //we have no state
                val secondAddressZip = "$secondAddress "
                secondAddressZip + zip
            }
        } // we have no city
    }
    return secondAddress
}