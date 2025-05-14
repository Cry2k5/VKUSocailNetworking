package com.dacs3.socialnetworkingvku.ui.components.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ModeComment
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.Color
@Composable
fun PostActions(
    isLiked: Boolean,
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        // Like Button — đổi icon và màu nếu đã like
        TextButton(
            onClick = onLikeClick,
            modifier = Modifier.weight(1f).padding(horizontal = 4.dp, vertical = 2.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Thích",
                    modifier = Modifier.size(18.dp),
                    tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Thích",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isLiked) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Comment Button
        PostActionButton(
            icon = Icons.Default.ModeComment,
            text = "Bình luận",
            onClick = onCommentClick,
            modifier = Modifier.weight(1f)
        )

        // Share Button
        PostActionButton(
            icon = Icons.Default.Share,
            text = "Chia sẻ",
            onClick = onShareClick,
            modifier = Modifier.weight(1f)
        )
    }
}
@Composable
private fun PostActionButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    TextButton(
        onClick = onClick,
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text, style = MaterialTheme.typography.labelLarge)
        }
    }
}
