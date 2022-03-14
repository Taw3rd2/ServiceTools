package com.waldorfprogramming.servicetools3.utilities

import android.util.Log
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun timestampToString(dateInput: Timestamp): String {
    Log.d("DateConverter", dateInput.toString())
    val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
    return sdf.format(dateInput)
}

fun javaDateToString(dateInput: Date): String {
    val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
    return sdf.format(dateInput)
}

fun javaDateToTimeString(dateInput: Date): String {
    val sdf = SimpleDateFormat("hh:mm:ss a", Locale.US)
    return sdf.format(dateInput)
}