package com.waldorfprogramming.servicetools3.adapters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun createWithFactory(
    create: () -> ViewModel
): ViewModelProvider.Factory{
    return object: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return create.invoke() as T
        }
    }
}