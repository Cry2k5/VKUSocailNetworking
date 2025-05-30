package com.dacs3.socialnetworkingvku.ui.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dacs3.socialnetworkingvku.security.TokenStoreManager
import com.dacs3.socialnetworkingvku.ui.components.NavigationBottom
import com.dacs3.socialnetworkingvku.ui.components.SearchBar
import com.dacs3.socialnetworkingvku.ui.components.TopAppBarHeader
import com.dacs3.socialnetworkingvku.ui.components.chat.MessageItem
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme
import com.dacs3.socialnetworkingvku.viewmodel.FollowerViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun MessageScreen(navController:NavController, followerViewModel:FollowerViewModel) {
    val following by followerViewModel.followingList.observeAsState(emptyList())
    val context = LocalContext.current
    val tokenStoreManager = remember { TokenStoreManager(context) }
    val currentUserId by tokenStoreManager.userIdFlow.collectAsState(initial = 0L)
    LaunchedEffect(Unit) {
        followerViewModel.getFollowing()
    }

    Scaffold(
        topBar = {
            TopAppBarHeader(content = "Nhắn tin")
        },
        bottomBar = {
            NavigationBottom(navController = navController)
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ) {
            SearchBar(
                content = "Tìm kiếm...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFF0F0F0))
            )

            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(following) { follow->
                    MessageItem(
                        name = follow.username,
                        avatar = follow.avatar?:"",
                        message = buildString {
                            if (!follow.lastMessage.isNullOrBlank()) {
                                if (follow.lastMessageSenderId == currentUserId) {
                                    append("Bạn: ")
                                }
                                append(follow.lastMessage)
                                follow.lastMessageTimestamp?.let {
                                    append(" ~ ${formatTimestamp(it)}")
                                }
                            } else {
                                append("Chưa có tin nhắn")
                            }
                        },
                        unreadCount = 1,
                        onClick = {
                            navController.navigate("chat/${currentUserId}/${follow.userId}/${follow.username}")
                        }
                    )
                }
            }
        }
    }
}
fun formatTimestamp(timestamp: Long): String {
    val dateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime()
    val formatter = DateTimeFormatter.ofPattern("HH:mm") // hoặc "dd/MM/yyyy HH:mm"
    return dateTime.format(formatter)
}

