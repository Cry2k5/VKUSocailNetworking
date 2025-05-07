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
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    var post =
        PostItem(
            username = "Nguyễn Văn A",
            date = 0L,
            imgAvatar = "",
            content = "Đây là nội dung bài viết có cả ảnh và video!",
            likeCount = 120,
            commentCount = 34,
            shareCount = 9,
            imgContent = "",
            onLikeClick = {},
            onCommentClick = {},
            onShareClick = {}

        )
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

               post
            }
        }

}
