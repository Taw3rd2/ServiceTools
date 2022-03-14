package com.waldorfprogramming.servicetools3.daily_service.daily_dispatch_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.waldorfprogramming.servicetools3.adapters.OnError
import com.waldorfprogramming.servicetools3.adapters.OnSuccess
import com.waldorfprogramming.servicetools3.adapters.createWithFactory
import com.waldorfprogramming.servicetools3.components.FABAddContent
import com.waldorfprogramming.servicetools3.components.ServiceToolsTopBar
import com.waldorfprogramming.servicetools3.daily_service.DispatchModel
import com.waldorfprogramming.servicetools3.daily_service.daily_dispatch_details.DailyDispatchDetailsActivity
import com.waldorfprogramming.servicetools3.daily_service.repository.DispatchRepository
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asStateFlow

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
class DailyDispatchListActivity: ComponentActivity() {

    private var techLead: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentSignIn = GoogleSignIn.getLastSignedInAccount(this)
        if(currentSignIn != null){
            techLead = currentSignIn.displayName.toString()
        }

        val viewModel: DailyDispatchListViewModel by lazy {
            ViewModelProvider(
                this,
                createWithFactory {
                    DailyDispatchListViewModel(
                        repository = DispatchRepository(),
                        techLead = techLead
                    )
                }
            ).get(DailyDispatchListViewModel::class.java)
        }

        setContent {
            ServiceTools3Theme {

                val context = LocalContext.current

                when (val dailyDispatchList = viewModel.dailyDispatchStateFlow.asStateFlow().collectAsState().value){

                    is OnError -> {
                        Log.d("firebase", "EquipmentListScreen: some thing went wrong")
                        Text(text = "try again when you have a better signal")
                    }
                    is OnSuccess -> {
                        val listOfDailyDispatches = dailyDispatchList.querySnapshot?.toObjects(
                            DispatchModel::class.java)
                        listOfDailyDispatches?.let {
                            Scaffold(
                                topBar = {
                                    ServiceToolsTopBar(title = "Today's Service")
                                }) {
                                LazyColumn {
                                    items(
                                        items = listOfDailyDispatches.sortedWith(compareBy { it.timeOfDay }),
                                        key = { dispatch -> dispatch.dispatchId }
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .animateItemPlacement(
                                                    animationSpec = tween(
                                                        durationMillis = 500
                                                    )
                                                )
                                        ) {
                                            DispatchListItem(it) {
                                            val dispatchDetailsIntent =
                                                Intent(context, DailyDispatchDetailsActivity::class.java)
                                                dispatchDetailsIntent.putExtra("DispatchId", it.dispatchId)
                                            context.startActivity(dispatchDetailsIntent)
                                        }
                                        }
                                    }
                                }
                            }
                        }
                    } else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                    }

                }

            }
        }
    }
}