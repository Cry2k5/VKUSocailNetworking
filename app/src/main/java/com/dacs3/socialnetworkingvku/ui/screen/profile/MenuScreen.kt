import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dacs3.socialnetworkingvku.R
import com.dacs3.socialnetworkingvku.data.user.UserDto
import com.dacs3.socialnetworkingvku.ui.components.NavigationBottom
import com.dacs3.socialnetworkingvku.viewmodel.AuthViewModel
import com.dacs3.socialnetworkingvku.viewmodel.UserViewModel

@Composable
fun MenuScreen(
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    navController: NavController
) {
    val context = LocalContext.current

    val userDto by userViewModel.userDtoFlow.collectAsState(
        initial = UserDto(0, "", "", "", "", "", "", "", "")
    )
    val userStats by userViewModel.userStats.observeAsState()
    val isSuccess by authViewModel.isSuccess

    LaunchedEffect(Unit) {
        if (userDto.userId == 0L) {
            userViewModel.getInfo()
        }
    }
    // Fetch user info and stats when userId is available
    LaunchedEffect(userDto.userId) {
        if (userDto.userId > 0) {
            userViewModel.resetStates()
            userViewModel.getInfo()
            userViewModel.getUserStats(userDto.userId)
        }
    }

    // Handle logout navigation
    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            authViewModel.resetStates()
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }
    }

    Scaffold(
        bottomBar = { NavigationBottom(navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .windowInsetsPadding(WindowInsets.safeDrawing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar
            AsyncImage(
                model = userDto.avatar.takeIf { !it.isNullOrBlank() } ?: R.drawable.avatar_default,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Black, CircleShape)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // User name (replace hardcoded name if needed)
            Text(text = userDto.username.ifBlank { "Người dùng" }, fontWeight = FontWeight.Bold, fontSize = 20.sp)

            Spacer(modifier = Modifier.height(16.dp))

            // User statistics
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                ProfileStat("Đã follow", userStats?.following?.toString() ?: "0")
                ProfileStat("Follower", userStats?.followers?.toString() ?: "0")
                ProfileStat("Đã thích", userStats?.likes?.toString() ?: "0")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Row 1 Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                ActionButton("Kho bài viết", Modifier.weight(1f)) {
                    Toast.makeText(context, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show()
                }
                ActionButton("Nhóm", Modifier.weight(1f)) {
                    Toast.makeText(context, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show()
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Row 2 Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                ActionButton("Sửa thông tin", Modifier.weight(1f)) {
                    navController.navigate("profile")
                }
                ActionButton("Đổi mật khẩu", Modifier.weight(1f)) {
                    navController.navigate("change_password")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Logout button
            Button(
                onClick = { authViewModel.logout(userDto.email) },
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
fun ActionButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text)
    }
}
