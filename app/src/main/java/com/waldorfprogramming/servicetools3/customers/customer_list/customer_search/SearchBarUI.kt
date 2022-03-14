package com.waldorfprogramming.servicetools3.customers.customer_list.customer_search

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun SearchBarUI(
    searchText: String,
    placeholderText: String,
    onSearchTextChanged: (String) -> Unit = {},
    onClearClick: () -> Unit = {},
    matchesFound: Boolean,
    results: @Composable () -> Unit = {}
) {
    Box {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
        ) {
            SearchBar(
                searchText,
                placeholderText,
                onSearchTextChanged,
                onClearClick
            )

            if(matchesFound) {
                results()
            } else {
                if(searchText.isNotEmpty()) {
                    NoSearchResults()
                }
            }
        }
    }
}