package com.dacs3.socialnetworkingvku.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme

@Composable
fun NavigationBottom(navController: NavController) {
    NavigationBar(
        modifier = Modifier
            .navigationBarsPadding()
            .heightIn(min = 56.dp, max = 72.dp)
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Trang chủ") },
            label = { Text("Trang chủ") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("followers") },
            icon = { Icon(Icons.Default.Person, contentDescription = "Theo dõi") },
            label = { Text("Theo dõi") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("chat") },
            icon = { Icon(Icons.Default.Chat, contentDescription = "Tin nhắn") },
            label = { Text("Tin nhắn") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("notification") },
            icon = { Icon(Icons.Default.Notifications, contentDescription = "Thông báo") },
            label = { Text("Thông báo") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("menu") },
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Cá nhân") },
            label = { Text("Cá nhân") }
        )
    }
}
