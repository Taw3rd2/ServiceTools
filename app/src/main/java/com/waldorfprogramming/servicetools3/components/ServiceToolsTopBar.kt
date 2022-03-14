package com.waldorfprogramming.servicetools3.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.waldorfprogramming.servicetools3.NavigationList
import com.waldorfprogramming.servicetools3.R
import com.waldorfprogramming.servicetools3.auth.LoginActivity

@Composable
fun ServiceToolsTopBar(
    title: String,
    icon: ImageVector? = null,
    showProfile: Boolean = true,
    onBackArrowClicked:() -> Unit = {}
) {
    val context = LocalContext.current

    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if(showProfile){
                    Image(
                        painter = painterResource(id = R.drawable.ic_app_logo),
                        contentDescription = "Home",
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .scale(0.9f))
                }
                if(icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "back",
                        tint = Color.White,
                        modifier = Modifier.clickable { onBackArrowClicked.invoke() }
                    )
                }
                Text(
                    text = title,
                    color = Color.White.copy(alpha = 0.7f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )

            }
        },
        actions = {
            IconButton(onClick = {
                FirebaseAuth.getInstance().signOut().run {
                    context.startActivity(Intent(context, LoginActivity::class.java))
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_logout_24),
                    contentDescription = "Log Out",
                    tint = Color.White
                )
            }
        },
        backgroundColor = Color(0xFF004D40),
        elevation = 3.dp
    )
}