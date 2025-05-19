package com.dacs3.socialnetworkingvku.ui.screen.login_signup

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dacs3.socialnetworkingvku.ui.components.NavigationBottom
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme
import com.dacs3.socialnetworkingvku.viewmodel.AuthViewModel


@Composable
fun ChangePasswordScreen(navController: NavController, authViewModel: AuthViewModel) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember{ mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val isChangeSuccess by authViewModel.isSuccess

    LaunchedEffect(isChangeSuccess)
    {
        if(isChangeSuccess)
        {
            Toast.makeText(context, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show()
            authViewModel.resetStates()
            oldPassword = ""
            newPassword = ""
            confirmPassword = ""
            passwordVisible = false
        }
    }
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
            // Nhập mật khẩu cũ
            OutlinedTextField(
                value = oldPassword,
                onValueChange = { oldPassword = it },
                placeholder = { Text("Nhập mật khẩu cũ") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                placeholder = { Text("Nhập mật khẩu mới") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = null)
                    }
                }
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
                    when {
                        newPassword.length < 8 -> {
                            Toast.makeText(context, "Mật khẩu phải có ít nhất 8 ký tự", Toast.LENGTH_SHORT).show()
                        }
                        !newPassword.any { it.isDigit() } -> {
                            Toast.makeText(context, "Mật khẩu phải chứa ít nhất một chữ số", Toast.LENGTH_SHORT).show()
                        }
                        !newPassword.any { it.isLetter() } -> {
                            Toast.makeText(context, "Mật khẩu phải chứa ít nhất một chữ cái", Toast.LENGTH_SHORT).show()
                        }
                        !newPassword.any { "!@#\$%^&*()_+-=[]{};':\",.<>?/\\|".contains(it) } -> {
                            Toast.makeText(context, "Mật khẩu phải chứa ít nhất một ký tự đặc biệt", Toast.LENGTH_SHORT).show()
                        }
                        newPassword != confirmPassword -> {
                            Toast.makeText(context, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            authViewModel.changePassword(oldPassword, newPassword)
                        }
                    }
                }
                ,
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
