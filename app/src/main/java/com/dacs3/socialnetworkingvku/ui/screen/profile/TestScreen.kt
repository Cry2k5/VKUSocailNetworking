package com.dacs3.socialnetworkingvku.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme

@Composable
fun ProfileScreen(avatarPainter: Painter) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar
        Image(
            painter = avatarPainter,
            contentDescription = "Avatar",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )

        // Tên người dùng
        Text(text = "Hua Huynh Anh", fontWeight = FontWeight.Bold, fontSize = 20.sp)

        Spacer(modifier = Modifier.height(8.dp))

        // Thống kê
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            ProfileStat("16", "Đã follow")
            ProfileStat("16", "Follower")
            ProfileStat("16", "Đã thích")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nút Kho bài viết và Nhóm
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            OutlinedButton(onClick = { /* TODO */ }) {
                Icon(Icons.Default.Article, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Kho bài viết")
            }

            OutlinedButton(onClick = { /* TODO */ }) {
                Icon(Icons.Default.Groups, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Nhóm")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nút Sửa hồ sơ
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(48.dp)
        ) {
            Text("Sửa hồ sơ")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Nút Đổi mật khẩu
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(48.dp)
        ) {
            Text("Đổi mật khẩu")
        }

        Spacer(modifier = Modifier.weight(1f))

        // Thanh điều hướng dưới cùng
        BottomNavigationBar()
    }
}

@Composable
fun ProfileStat(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(text = label, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Trang chủ") },
            label = { Text("Trang chủ") },
            selected = true,
            onClick = { }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.PersonAdd, contentDescription = "Theo dõi") },
            label = { Text("Theo dõi") },
            selected = false,
            onClick = { }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Chat, contentDescription = "Tin nhắn") },
            label = { Text("Tin nhắn") },
            selected = false,
            onClick = { }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Notifications, contentDescription = "Thông báo") },
            label = { Text("Thông báo") },
            selected = false,
            onClick = { }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Cá nhân") },
            label = { Text("Cá nhân") },
            selected = false,
            onClick = { }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    VKUSocialNetworkingTheme {
        ProfileScreen(
            avatarPainter = painterResource(android.R.drawable.sym_def_app_icon) // Sử dụng ảnh có sẵn để tránh lỗi preview
        )
    }
}
