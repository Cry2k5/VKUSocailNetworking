package com.dacs3.socialnetworkingvku.ui.screen.followers

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dacs3.socialnetworkingvku.ui.components.NavigationBottom
import com.dacs3.socialnetworkingvku.ui.components.TopAppBarHeader
import com.dacs3.socialnetworkingvku.ui.components.TitleSmallCustom
import com.dacs3.socialnetworkingvku.ui.components.followers.PersonItem
import com.dacs3.socialnetworkingvku.viewmodel.FollowerViewModel
import kotlinx.coroutines.coroutineScope

@Composable
fun FollowersScreen(navController: NavController, followerViewModel: FollowerViewModel) {
    val following by followerViewModel.followingList.observeAsState(emptyList())
    val peopleList by followerViewModel.peopleList.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        followerViewModel.getFollowing()
        followerViewModel.getPeople()
    }

    Scaffold(
        topBar = {
            TopAppBarHeader(
                content = "Followers",
                actionIcons = {
                    IconButton(onClick = { /* Search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBottom(navController)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            item {
                TitleSmallCustom("Đã follow")
            }

            items(following) { follower ->
                PersonItem(
                    name = follower.username,
                    avatar = follower.avatar,
                    nickname = null,
                    isFollowed = true,
                    onUnfollowClick = {
                        Log.d("FollowersScreen", "Unfollow clicked for user: ${follower.username}")
                        Log.d("FollowersScreen", "User ID: ${follower.userId}")
                        followerViewModel.unfollow(follower.userId)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleSmallCustom("Gợi ý cho bạn")
                    Text("Xem tất cả", color = Color.Blue)
                }
            }

            items(peopleList) { person ->
                PersonItem(
                    name = person.username,
                    avatar = person.avatar,
                    nickname = null,
                    isFollowed = false,
                    onFollowClick = {
                        followerViewModel.follow(person.userId)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}
