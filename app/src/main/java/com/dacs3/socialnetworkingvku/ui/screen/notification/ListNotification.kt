package com.dacs3.socialnetworkingvku.ui.screen.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dacs3.socialnetworkingvku.ui.components.NavigationBottom
import com.dacs3.socialnetworkingvku.ui.components.followers.PersonItem
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme

@Composable
fun ListNotification(
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        bottomBar = { NavigationBottom() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Quay lại"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Thông báo",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f),
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            repeat(10) { index ->
                val isComment = index > 3
                PersonItem(
                    name = if (isComment) "Trịnh Quyết Chiến đã bình luận ảnh của bạn" else "Trịnh Quyết Chiến đã theo dõi bạn",
                    isFollowed = false,
                    nickname = null,
                    actionIcon = {
                        IconButton(onClick = { /* tắt thông báo */ }) {
                            Icon(
                                imageVector = Icons.Default.NotificationsOff,
                                contentDescription = "Tắt thông báo",
                                tint = Color.Black
                            )
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListNotificationPreview() {
    VKUSocialNetworkingTheme {
        ListNotification()
    }
}

