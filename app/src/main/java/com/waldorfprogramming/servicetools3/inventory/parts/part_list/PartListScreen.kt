package com.waldorfprogramming.servicetools3.inventory.parts.part_list

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.waldorfprogramming.servicetools3.adapters.OnError
import com.waldorfprogramming.servicetools3.adapters.OnSuccess
import com.waldorfprogramming.servicetools3.components.FABAddContent
import com.waldorfprogramming.servicetools3.inventory.parts.PartModel
import com.waldorfprogramming.servicetools3.inventory.parts.part_list.part_search.PartSearchActivity
import kotlinx.coroutines.flow.asStateFlow

@ExperimentalFoundationApi
@Composable
fun PartListScreen(viewModel: PartListViewModel) {

    val context = LocalContext.current

    when (val partList = viewModel.partsStateFlow.asStateFlow().collectAsState().value) {

        is OnError -> {
            Log.d("firebase", "PartListScreen: some thing went wrong")
            Text(text = "try again when you have a better signal")
        }
        is OnSuccess -> {
            val listOfParts = partList.querySnapshot?.toObjects(PartModel::class.java)
            listOfParts?.let {
                Scaffold(
                    topBar = {
                        PartsListTopBar( onSearchBarClick = {
                            context.startActivity(Intent(context,
                                PartSearchActivity::class.java))
                        }, listOfParts.size )
                    },
                    floatingActionButton = {
                        FABAddContent(
                            contentDescription = "Add New Part") {
                            //context.startActivity(Intent(context, PartCreate::class.java))
                        }
                    }) {
                    ListOfParts(
                        parts = listOfParts
                    )
                }
            }
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}