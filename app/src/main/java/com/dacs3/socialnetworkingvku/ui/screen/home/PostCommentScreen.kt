package com.dacs3.socialnetworkingvku.ui.screen.home

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dacs3.socialnetworkingvku.R
import com.dacs3.socialnetworkingvku.ui.components.Chat_CommentCustom
import com.dacs3.socialnetworkingvku.ui.components.TopAppBarHeader
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme
import com.dacs3.socialnetworkingvku.viewmodel.PostViewModel
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PostCommentScreen(postId: Long, navController: NavController, postViewModel: PostViewModel) {
    var commentText by remember { mutableStateOf("") }
    val result by postViewModel.comments.observeAsState()
    val comments = result?.getOrNull() ?: emptyList()

    val isCommentLoad by postViewModel.isCommentLoading
    val isCommented by postViewModel.isCommentSuccess

    var hasHandledCommentSuccess by remember { mutableStateOf(false) }

    // Log postId for debugging
    Log.d("PostCommentScreen", "Post ID: $postId")

    val context = LocalContext.current
    // Refresh comments when a new comment is posted
    LaunchedEffect(isCommented) {
        if (isCommented && !hasHandledCommentSuccess) {
            postViewModel.getCommentsForPost(postId)
            hasHandledCommentSuccess = true
        }
    }

    // Initial comments load
    LaunchedEffect(postId) {
        postViewModel.getCommentsForPost(postId)
    }

    // Reset comment state when navigating back
    IconButton(onClick = {
        postViewModel.resetState()
        navController.popBackStack()
    }) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
    }

    Scaffold(
        topBar = {
            TopAppBarHeader(
                content = "Comment",
                navigationIcon = {
                    IconButton(onClick = {
                        postViewModel.resetState()
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Chat_CommentCustom(
                text = commentText,
                onTextChange = { commentText = it },
                onSendClick = {
                    if (commentText.isNotBlank() && !isCommentLoad) {
                        postViewModel.commentPost(postId, commentText)
                        commentText = ""
                        hasHandledCommentSuccess =
                            false // Allow re-triggering comment fetch after comment sent
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding() // đảm bảo không bị che bởi bàn phím
                    .navigationBarsPadding()
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Divider()

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(comments) { comment ->
                    val avatarRequest = remember(comment.user.avatar) {
                        ImageRequest.Builder(context = context)
                            .data(comment.user.avatar.takeIf { !it.isNullOrBlank() } ?: R.drawable.avatar_default)
                            .size(256)
                            .crossfade(true)
                            .build()
                    }
                    Row(modifier = Modifier.padding(vertical = 4.dp)) {
                        AsyncImage(
                            model = avatarRequest,
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(comment.user.username, fontWeight = FontWeight.Bold)
                            Surface(
                                color = Color(0xFFF0F0F0),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.padding(top = 2.dp)
                            ) {
                                Text(comment.content, modifier = Modifier.padding(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
