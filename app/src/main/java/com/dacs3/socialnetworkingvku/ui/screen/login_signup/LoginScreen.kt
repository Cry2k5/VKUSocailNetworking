package com.dacs3.socialnetworkingvku.ui.screen.login_signup

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.dacs3.socialnetworkingvku.ui.components.login_signup.SignInWithGoogleScreen
import com.dacs3.socialnetworkingvku.ui.components.login_signup.ButtonCustom
import com.dacs3.socialnetworkingvku.ui.components.login_signup.CustomTextField
import com.dacs3.socialnetworkingvku.viewmodel.AuthViewModel
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import kotlin.math.log

@Composable
fun LoginScreen(viewModel: AuthViewModel, navController: NavController) {
    // Lấy Context để hiển thị Toast
    val context = LocalContext.current

    // Lấy giá trị từ ViewModel
    val isLoading by viewModel.isLoading
    val isSuccess by viewModel.isSuccess
    val errorMessage by viewModel.errorMessage

    // Khai báo state cho email và password
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Hiển thị Toast khi có sự thay đổi trạng thái
    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            viewModel.resetStates()
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }

        }
    }


    // UI của màn hình login
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Mạng xã hội,\nkết nối đam mê.",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 36.sp
        )

        // Input email
        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email"
        )

        // Input password
        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Mật khẩu",
            visualTransformation = PasswordVisualTransformation()
        )

        Text(
            text = "Quên mật khẩu",
            color = Color.Blue,
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    navController.navigate("forgot_password") // route này bạn sẽ định nghĩa trong NavHost
                }
        )

        // Button đăng nhập
        ButtonCustom(
            text = "Đăng nhập",
            onClick = {
                viewModel.login(email, password)
            }
        )

        // Đăng ký tài khoản mới
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text("Không có tài khoản? ")
            Text(
                "Đăng kí tại đây",
                color = Color.Blue,
                modifier = Modifier.clickable {
                    navController.navigate("register")

                }
            )
        }

        Divider()

        // Đăng nhập với Google
        SignInWithGoogleScreen()

        // Hiển thị CircularProgressIndicator khi đang đăng nhập
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}
