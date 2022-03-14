package com.waldorfprogramming.servicetools3.inventory.navigation_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.waldorfprogramming.servicetools3.adapters.OnError
import com.waldorfprogramming.servicetools3.adapters.OnSuccess
import com.waldorfprogramming.servicetools3.adapters.createWithFactory
import com.waldorfprogramming.servicetools3.components.DetailLabel
import com.waldorfprogramming.servicetools3.components.FABAddContent
import com.waldorfprogramming.servicetools3.components.GradientButton
import com.waldorfprogramming.servicetools3.components.ServiceToolsTopBar
import com.waldorfprogramming.servicetools3.inventory.inventory_containers.InventoryContainerModel
import com.waldorfprogramming.servicetools3.inventory.parts.part_list.PartListActivity
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme
import com.waldorfprogramming.servicetools3.ui.theme.buttonGradientColor1
import com.waldorfprogramming.servicetools3.ui.theme.buttonGradientColor2
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asStateFlow

@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
class InventoryNavigationList: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: InventoryNavigationViewModel by lazy {
            ViewModelProvider(
                this,
                createWithFactory {
                    InventoryNavigationViewModel(
                        repository = InventoryNavigationRepository()
                    )
                }
            ).get(InventoryNavigationViewModel::class.java)
        }

        setContent {
            ServiceTools3Theme {

                val context = LocalContext.current

                Scaffold(
                    topBar = {
                        ServiceToolsTopBar(title = "Inventory")
                    },
                    floatingActionButton = {
                        FABAddContent(
                            contentDescription = "Add New Customer") {
                            //TODO context.startActivity(Intent(context, CustomerCreate::class.java))
                        }
                    }
                ) {
                    Column(
                        modifier = Modifier
                            //.verticalScroll(rememberScrollState())
                            .background(color = MaterialTheme.colors.background)
                            .padding(bottom = 64.dp)
                    ) {

                        Spacer(modifier = Modifier.padding(top = 4.dp))
                        DetailLabel(label = "Catalogs")

                        //Parts Catalog
                        GradientButton(
                            text = "Parts Catalog",
                            textColor = MaterialTheme.colors.onSecondary,
                            gradient = Brush.horizontalGradient(
                                colors = listOf(
                                    buttonGradientColor1,
                                    buttonGradientColor2
                                )
                            ),
                            onClick = {
                                context.startActivity(Intent(context, PartListActivity::class.java))
                            }
                        )

                        //Equipment Catalog
                        GradientButton(
                            text = "Equipment Catalog",
                            textColor = MaterialTheme.colors.onSecondary,
                            gradient = Brush.horizontalGradient(
                                colors = listOf(
                                    buttonGradientColor1,
                                    buttonGradientColor2
                                )
                            ),
                            onClick = {
                                //TODO context.startActivity(Intent(context, PartActivity::class.java))
                            }
                        )

                        DetailLabel(label = "Stock")

                        //Additional Containers
                        when (val containerList = viewModel.containerStateFlow.asStateFlow().collectAsState().value) {

                            is OnError -> {
                                Log.d("firebase", "EquipmentListScreen: some thing went wrong")
                                Text(text = "try again when you have a better signal")
                            }
                            is OnSuccess -> {
                                val listOfContainers = containerList.querySnapshot?.toObjects(
                                    InventoryContainerModel::class.java)
                                listOfContainers?.let {
                                    ListOfInventoryContainers(listOfContainers)
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
                        Spacer(modifier = Modifier.padding(64.dp))
                    }
                }
            }
        }
    }
}