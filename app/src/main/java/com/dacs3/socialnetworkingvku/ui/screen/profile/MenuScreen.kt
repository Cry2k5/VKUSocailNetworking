
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dacs3.socialnetworkingvku.R
import com.dacs3.socialnetworkingvku.ui.components.NavigationBottom

@Composable
fun MenuScreen(
    navController: NavController,
    onLogoutClick: () -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            NavigationBottom(navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .windowInsetsPadding(WindowInsets.safeDrawing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar
            Image(
                painter = painterResource(id = R.drawable.demo_image_background), // Ảnh demo
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Black, CircleShape)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tên người dùng
            Text(text = "Hua Huynh Anh", fontWeight = FontWeight.Bold, fontSize = 20.sp)

            Spacer(modifier = Modifier.height(16.dp))

            // Thống kê
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                ProfileStat(title = "Đã follow", value = "16")
                ProfileStat(title = "Follower", value = "16")
                ProfileStat(title = "Đã thích", value = "16")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Hàng 1
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                ActionButton(text = "📁 Kho bài viết", modifier = Modifier.weight(1f))
                ActionButton(text = "👥 Nhóm", modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Hàng 2
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                ActionButton(text = "Sửa thông tin", modifier = Modifier.weight(1f))
                ActionButton(text = "Đổi mật khẩu", modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Nút đăng xuất
            Button(
                onClick = { onLogoutClick() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, Color.Red),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Đăng xuất", color = Color.Red)
            }
        }
    }
}


@Composable
fun ProfileStat(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontWeight = FontWeight.Bold)
        Text(text = title, fontSize = 12.sp)
    }
}

@Composable
fun ActionButton(text: String, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = { /* TODO: Xử lý sự kiện */ },
        modifier = modifier
    ) {
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MenuScreen(navController = rememberNavController())
}
