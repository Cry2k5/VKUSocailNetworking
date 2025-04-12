package com.dacs3.socialnetworkingvku.ui.components.followers


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PersonItem(
    name: String,
    nickname: String? = null, // Cho phép null
    isFollowed: Boolean,
    onFollowClick: () -> Unit = {},
    onMessageClick: () -> Unit = {},
    onUnfollowClick: () -> Unit = {},
    actionIcon: (@Composable (() -> Unit))? = null // Thêm icon tuỳ chỉnh (như chuông tắt)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(name, fontWeight = FontWeight.Bold)
            if (!nickname.isNullOrEmpty()) {
                Text(nickname, color = Color.Gray, fontSize = 12.sp)
            }
        }

        actionIcon?.invoke() ?: run {
            if (isFollowed) {
                IconButton(onClick = onMessageClick) {
                    Icon(Icons.Default.Message, contentDescription = "Nhắn tin", tint = Color.Blue)
                }
                IconButton(onClick = onUnfollowClick) {
                    Icon(Icons.Default.PersonRemove, contentDescription = "Hủy theo dõi", tint = Color.Red)
                }
            } else {
                Button(
                    onClick = onFollowClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    )
                ) {
                    Text("Theo dõi")
                }
            }
        }
    }
}


