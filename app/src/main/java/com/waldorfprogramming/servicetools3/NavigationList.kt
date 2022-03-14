package com.waldorfprogramming.servicetools3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.waldorfprogramming.servicetools3.charging_tool.ChargingTool
import com.waldorfprogramming.servicetools3.components.GradientButton
import com.waldorfprogramming.servicetools3.components.ServiceToolsTopBar
import com.waldorfprogramming.servicetools3.customers.customer_list.CustomerListActivity
import com.waldorfprogramming.servicetools3.daily_service.daily_dispatch_list.DailyDispatchListActivity
import com.waldorfprogramming.servicetools3.inventory.navigation_list.InventoryNavigationList
import com.waldorfprogramming.servicetools3.ui.theme.ServiceTools3Theme
import com.waldorfprogramming.servicetools3.ui.theme.buttonGradientColor1
import com.waldorfprogramming.servicetools3.ui.theme.buttonGradientColor2
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
class NavigationList : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ServiceTools3Theme {
                Surface(color = MaterialTheme.colors.background) {
                    NavList()
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
@Composable
fun NavList() {
    Scaffold(
        topBar = {
            ServiceToolsTopBar(title = "Service Tools 3")
        }
    ) {
        val context = LocalContext.current
        Column (
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colors.background)
                .padding(bottom = 64.dp)
        ){

            Spacer(modifier = Modifier.padding(16.dp))

            //Daily Service
            GradientButton(
                text = "Daily Service",
                textColor = Color.White,
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        buttonGradientColor1,
                        buttonGradientColor2
                    )
                ),
                onClick = {
                    context.startActivity(Intent(context, DailyDispatchListActivity::class.java))
                }
            )

            //Weekly Service
            GradientButton(
                text = "Weekly Service",
                textColor = Color.White,
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        buttonGradientColor1,
                        buttonGradientColor2
                    )
                ),
                onClick = {
                    //context.startActivity(Intent(context, WeeklyActivity::class.java))
                }
            )

            // Customer List
            GradientButton(
                text = "Customer List",
                textColor = Color.White,
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        buttonGradientColor1,
                        buttonGradientColor2
                    )
                ),
                onClick = {
                    context.startActivity(Intent(context, CustomerListActivity::class.java))
                }
            )

            //Inventory
            GradientButton(
                text = "Inventory",
                textColor = Color.White,
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        buttonGradientColor1,
                        buttonGradientColor2
                    )
                ),
                onClick = {
                    context.startActivity(Intent(context, InventoryNavigationList::class.java))
                }
            )

            //Charging Calculator
            GradientButton(
                text = "Charging Calculator",
                textColor = Color.White,
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        buttonGradientColor1,
                        buttonGradientColor2
                    )
                ),
                onClick = {
                    context.startActivity(Intent(context, ChargingTool::class.java))
                }
            )

            Spacer(modifier = Modifier.padding(64.dp))
        }
    }
}