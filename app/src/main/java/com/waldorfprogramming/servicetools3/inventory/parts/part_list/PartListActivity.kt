package com.waldorfprogramming.servicetools3.inventory.parts.part_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.ViewModelProvider
import com.waldorfprogramming.servicetools3.adapters.createWithFactory
import com.waldorfprogramming.servicetools3.inventory.parts.parts_repository.PartsRepository
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
class PartListActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val model: PartListViewModel by lazy {
            ViewModelProvider(
                this,
                createWithFactory {
                    PartListViewModel(repository = PartsRepository())
                }
            ).get(PartListViewModel::class.java)
        }

        setContent {
            ServiceTools3Theme {
                PartListScreen(model)
            }
        }

    }
}