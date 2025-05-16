package com.dacs3.socialnetworkingvku.ui.components.followers


import androidx.compose.foundation.Image
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
import coil.compose.rememberAsyncImagePainter
import com.dacs3.socialnetworkingvku.R

@Composable
fun PersonItem(
    name: String,
    avatar: String?,
    nickname: String?,
    isFollowed: Boolean,
    onFollowClick: () -> Unit = {},
    onMessageClick: () -> Unit = {},
    onUnfollowClick: () -> Unit = {},
    actionIcon: (@Composable (() -> Unit))? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar từ URL
        val avatarPainter = rememberAsyncImagePainter(
            model = avatar.takeIf { !it.isNullOrBlank() } ?: R.drawable.avatar_default
        )

        Image(
            painter = avatarPainter,
            contentDescription = "Avatar",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            // Thêm mô tả phụ ở đây nếu cần
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



