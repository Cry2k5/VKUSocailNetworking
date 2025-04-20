package com.dacs3.socialnetworkingvku.ui.components.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun PostStats(
    likeCount: Int,
    commentCount: Int,
    shareCount: Int
) {
    val statsText = "$likeCount thích • $commentCount bình luận • $shareCount chia sẻ"
    Text(
        text = statsText,
        fontSize = 12.sp,
        color = Color.Gray
    )
}
