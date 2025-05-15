package com.dacs3.socialnetworkingvku.ui.screen.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dacs3.socialnetworkingvku.ui.components.NavigationBottom
import com.dacs3.socialnetworkingvku.ui.components.SearchBar
import com.dacs3.socialnetworkingvku.ui.components.TopAppBarHeader
import com.dacs3.socialnetworkingvku.ui.components.chat.MessageItem
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme
import com.dacs3.socialnetworkingvku.viewmodel.FollowerViewModel

@Composable
fun MessageScreen(navController:NavController, followerViewModel:FollowerViewModel) {
    val following by followerViewModel.followingList.observeAsState(emptyList())

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
            SearchBar("Tìm kiếm...")

            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(following) { follow->
                    MessageItem(
                        name = follow.username,
                        avatar = follow.avatar?:"",
                        message = "",
                        unreadCount = 9,
                        onClick = {
                            navController.navigate("chat/${follow.userId}/${follow.username}")
                        }
                    )
                }
            }
        }
    }
}
