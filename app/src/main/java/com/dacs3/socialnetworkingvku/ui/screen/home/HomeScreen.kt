package com.dacs3.socialnetworkingvku.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dacs3.socialnetworkingvku.ui.components.NavigationBottom
import com.dacs3.socialnetworkingvku.ui.components.SearchBar
import com.dacs3.socialnetworkingvku.ui.components.home.PostItem
import com.dacs3.socialnetworkingvku.viewmodel.PostViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: PostViewModel = viewModel()) {
    val posts by viewModel.posts.collectAsState()

    Scaffold(
        topBar = {
            SearchBar(
                content = "Tìm kiếm...",
            )
        },
        bottomBar = { NavigationBottom() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(posts) { post ->
                PostItem(
                    username = post.username,
                    date = post.date,
                    imgAvatar = post.imgAvatar,
                    content = post.content,
                    imgContent = post.imgContent?:"",
                    likeCount = post.likeCount,
                    commentCount = post.commentCount,
                    shareCount = post.shareCount
                )
            }
        }
    }
}
