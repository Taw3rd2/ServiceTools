package com.waldorfprogramming.servicetools3.inventory.parts.part_list.part_search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.waldorfprogramming.servicetools3.adapters.createWithFactory
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme

class PartSearchActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val model: PartSearchViewModel by lazy {
            ViewModelProvider(
                this,
                createWithFactory {
                    PartSearchViewModel(repository = PartSearchRepository())
                }
            ).get(PartSearchViewModel::class.java)
        }

        setContent {
            ServiceTools3Theme {

            }
        }
    }
}