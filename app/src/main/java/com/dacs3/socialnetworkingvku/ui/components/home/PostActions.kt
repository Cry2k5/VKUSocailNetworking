package com.dacs3.socialnetworkingvku.ui.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PostActions(
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextButton(onClick = onLikeClick) {
            Text("👍 Thích")
        }
        TextButton(onClick = onCommentClick) {
            Text("💬 Bình luận")
        }
        TextButton(onClick = onShareClick) {
            Text("↪ Chia sẻ")
        }
    }
}
