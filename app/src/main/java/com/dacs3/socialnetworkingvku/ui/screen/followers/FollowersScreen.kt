package com.dacs3.socialnetworkingvku.ui.screen.followers

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dacs3.socialnetworkingvku.ui.components.NavigationBottom
import com.dacs3.socialnetworkingvku.ui.components.TopAppBarHeader
import com.dacs3.socialnetworkingvku.ui.components.*
import com.dacs3.socialnetworkingvku.ui.components.followers.PersonItem
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme
@Composable
fun FollowersScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBarHeader(
                content = "Followers",
                actionIcons = {
                    IconButton(onClick = { /* Search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                })
        },
        bottomBar = {
            NavigationBottom(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()) // ✅ Cuộn nếu nội dung dài
        ) {

            // Danh sách đã follow
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                TitleSmallCustom("Đã follow")
                repeat(5) {
                    PersonItem(
                        name = "Hứa Huỳnh Anh",
                        isFollowed = true,
                        onMessageClick = { /* ... */ },
                        onUnfollowClick = { /* ... */ }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Gợi ý cho bạn
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleSmallCustom("Gợi ý cho bạn")
                    Text("Xem tất cả", color = Color.Blue)
                }
                repeat(3) {
                    PersonItem(
                        name = "Trịnh Quyết Chiến",
                        isFollowed = false,
                        onFollowClick = { /* ... */ }
                    )
                }
            }

            Spacer(modifier = Modifier.height(80.dp)) // Tránh bị che bởi bottom nav
        }
    }
}


