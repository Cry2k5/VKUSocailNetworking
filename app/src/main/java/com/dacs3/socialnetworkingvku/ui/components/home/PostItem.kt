package com.dacs3.socialnetworkingvku.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dacs3.socialnetworkingvku.R
import com.dacs3.socialnetworkingvku.roomdata.post.PostEntity
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme
import com.dacs3.socialnetworkingvku.viewmodel.AuthViewModel
import com.dacs3.socialnetworkingvku.viewmodel.PostViewModel

@Composable
fun PostItem(
    post: PostEntity,
    onLikeClick: (Long) -> Unit = {},
    onCommentClick: (Long) -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    Column(modifier = Modifier.padding(16.dp)) {
        PostHeader(username = post.userEmail, date = post.createdAt, imgAvatar = post.userAvatar)
        Spacer(modifier = Modifier.height(8.dp))
        PostContent(content = post.content, imgContent = post.image)
        Spacer(modifier = Modifier.height(8.dp))
        PostStats(
            likeCount = post.likeCount,
            commentCount = post.commentCount,
            shareCount = 0
        )
        Spacer(modifier = Modifier.height(4.dp))
        PostActions(
            onLikeClick = { onLikeClick(post.postId)},
            onCommentClick = {onCommentClick(post.postId)},
            onShareClick = onShareClick
        )
    }
}



