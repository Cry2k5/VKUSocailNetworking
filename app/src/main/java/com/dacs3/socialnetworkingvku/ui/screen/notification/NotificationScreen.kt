package com.dacs3.socialnetworkingvku.ui.screen.notification
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dacs3.socialnetworkingvku.ui.components.NavigationBottom
import com.dacs3.socialnetworkingvku.ui.components.TitleSmallCustom
import com.dacs3.socialnetworkingvku.ui.components.followers.PersonItem
import com.dacs3.socialnetworkingvku.ui.components.login_signup.ButtonCustom
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme

@Composable
fun NotificationScreen() {
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

            Text(
                text = "Thông báo",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))
            TitleSmallCustom(title = "Hôm nay")

            PersonItem(
                name = "Trịnh Quyết Chiến đã theo dõi bạn",
                isFollowed = false,
                nickname = null,
                actionIcon = {
                    IconButton(onClick = { /* Tắt thông báo */ }) {
                        Icon(
                            imageVector = Icons.Default.NotificationsOff,
                            contentDescription = "Tắt thông báo",
                            tint = Color.Black
                        )
                    }
                }
            )

            PersonItem(
                name = "Trịnh Quyết Chiến đã bình luận ảnh của bạn",
                isFollowed = false,
                nickname = null,
                actionIcon = {
                    IconButton(onClick = { /* Tắt thông báo */ }) {
                        Icon(
                            imageVector = Icons.Default.NotificationsOff,
                            contentDescription = "Tắt thông báo",
                            tint = Color.Black
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
            TitleSmallCustom(title = "Trước đây")

            repeat(5) {
                PersonItem(
                    name = "Trịnh Quyết Chiến đã bình luận ảnh của bạn",
                    isFollowed = false,
                    nickname = null,
                    actionIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.NotificationsOff,
                                contentDescription = "Tắt thông báo",
                                tint = Color.Black
                            )
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            ButtonCustom(
                text = "Xem tất cả thông báo trước đó",
                onClick = { /* Xử lý khi nhấn */ },
                containerColor = Color(0xFFE5E5EA),
                contentColor = Color.Black.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotificationPreview() {
    VKUSocialNetworkingTheme {
        NotificationScreen()
    }
}

