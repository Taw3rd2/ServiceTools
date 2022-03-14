package com.waldorfprogramming.servicetools3.customers.customer_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.waldorfprogramming.servicetools3.R

@Composable
fun CustomerListTopBar(
    onSearchBarClick: () -> Unit,
    customerCount: Int
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_app_logo),
                    contentDescription = "Home",
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .scale(0.9f)
                )
                Text(
                    text = "Customer List $customerCount",
                    color = Color.White.copy(alpha = 0.7f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
            }
        },
        actions = {
            IconButton(
                modifier = Modifier,
                onClick = { onSearchBarClick() }
            ) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Search Customers",
                    tint = Color.White
                )
            }
        },
        backgroundColor = Color(0xFF004D40),
        elevation = 3.dp
    )
}