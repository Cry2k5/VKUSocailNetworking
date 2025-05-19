package com.dacs3.socialnetworkingvku.ui.screen.login_signup

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dacs3.socialnetworkingvku.ui.components.NavigationBottom
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme



@Composable
fun ChangePasswordScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    var newPassword by remember{ mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = { NavigationBottom(navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState)
                .windowInsetsPadding(WindowInsets.safeDrawing),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Tiêu đề
            Text(
                text = "Đổi mật khẩu",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

            // Mô tả
            Text(
                text = "Mật khẩu phải tối thiểu 8 kí tự, bao gồm cả chữ số, chữ cái, và kí tự đặc biệt (!\$@%)",
                fontSize = 14.sp,
                color = Color.Gray
            )

            // Nhập mật khẩu mới
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                placeholder = { Text("Nhập mật khẩu mới") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            // Nhập lại mật khẩu mới
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("Nhập lại mật khẩu mới") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            // Nút đổi mật khẩu
            Button(
                onClick = {
                    if (newPassword.length < 8 || !newPassword.any { it.isDigit() } || !newPassword.any { it.isLetter() } || !newPassword.any { "!@#\$%^&*()_+-=[]{};':\",.<>?/\\|".contains(it) }) {
                        Toast.makeText(context, "Mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show()
                    } else if (newPassword != confirmPassword) {
                        Toast.makeText(context, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show()
                        // TODO: Gọi API đổi mật khẩu tại đây
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF))
            ) {
                Text("Đổi mật khẩu", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChangePasswordScreenPreview() {
    val navController = rememberNavController()
    VKUSocialNetworkingTheme {
        ChangePasswordScreen(navController =navController )
    }
}