package com.dacs3.socialnetworkingvku.ui.screen.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dacs3.socialnetworkingvku.R
import com.dacs3.socialnetworkingvku.data.User
import com.dacs3.socialnetworkingvku.ui.components.NavigationBottom
import com.dacs3.socialnetworkingvku.ui.components.SearchBar
import com.dacs3.socialnetworkingvku.ui.components.home.PostItem
import com.dacs3.socialnetworkingvku.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: AuthViewModel, controller: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val user by viewModel.user.collectAsState(initial = User(0, "", "", ""))
    val isLoading by viewModel.isLoading
    val isSuccess by viewModel.isSuccess

    val context = LocalContext.current
    val avatarRequest = remember(user.avatar) {
        ImageRequest.Builder(context)
            .data(user.avatar.takeIf { !it.isNullOrBlank() } ?: R.drawable.avatar_default)
            .crossfade(true)
            .build()
    }


    // Khi logout thành công, chuyển hướng về login
    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            viewModel.resetStates()
            controller.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

                    AsyncImage(
                        model = avatarRequest,
                        contentDescription = "Avatar người dùng",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = user.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = user.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Divider(
                        color = MaterialTheme.colorScheme.outline,
                        thickness = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                OutlinedButton(
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            viewModel.logout(user.email)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Icon(Icons.Default.Logout, contentDescription = "Logout")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Đăng xuất")
                }
            }
        }
        ,
        gesturesEnabled = true,
        scrimColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f)
    ) {
        Scaffold(
            topBar = { SearchBar(content = "Tìm kiếm...") },
            bottomBar = { NavigationBottom() }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Avatar header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable { scope.launch { drawerState.open() } },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = avatarRequest,
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "Bạn đang nghĩ gì?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    item {
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
                    }
                }
            }
        }
    }
}
