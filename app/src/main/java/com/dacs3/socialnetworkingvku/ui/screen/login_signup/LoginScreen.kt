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
    val isLoading by viewModel.isLoading
    val isSuccess by viewModel.isSuccess
    val errorMessage by viewModel.errorMessage
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var hasSubmitted by remember { mutableStateOf(false) }

    val isEmailValid = remember(email) {
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    val googleLoginSuccess by viewModel.googleLoginSuccess
    val googleLoginErrorMessage by viewModel.googleLoginErrorMessage

    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            viewModel.resetStates()
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }
    LaunchedEffect(googleLoginSuccess) {
        when (googleLoginSuccess) {
            true -> {
                Toast.makeText(context, "Đăng nhập Google thành công!", Toast.LENGTH_SHORT).show()
                viewModel.resetGoogleLoginState()
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
            false -> {
                Toast.makeText(context, "Đăng nhập Google thất bại: $googleLoginErrorMessage", Toast.LENGTH_LONG).show()
                viewModel.resetGoogleLoginState()
            }
            null -> Unit
        }
    }
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

        // Email input
        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email"
        )
        if (hasSubmitted && email.isBlank()) {
            Text("Vui lòng nhập email.", color = Color.Red, fontSize = 12.sp)
        } else if (hasSubmitted && !isEmailValid) {
            Text("Email không hợp lệ.", color = Color.Red, fontSize = 12.sp)
        }

        // Password input
        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Mật khẩu",
            visualTransformation = PasswordVisualTransformation()
        )
        if (hasSubmitted && password.isBlank()) {
            Text("Vui lòng nhập mật khẩu.", color = Color.Red, fontSize = 12.sp)
        }

        Text(
            text = "Quên mật khẩu",
            color = Color.Blue,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { navController.navigate("forgot_password") }
        )

        // Lỗi từ ViewModel (sai tài khoản, mật khẩu)
        if (!errorMessage.isNullOrEmpty() && hasSubmitted && email.isNotBlank() && password.isNotBlank() && isEmailValid) {
            Text(
                text = "Sai email hoặc mật khẩu.",
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // Button login
        ButtonCustom(
            text = "Đăng nhập",
            onClick = {
                hasSubmitted = true
                if (email.isNotBlank() && isEmailValid && password.isNotBlank()) {
                    viewModel.login(email, password)
                }
            }
        )

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text("Không có tài khoản? ")
            Text(
                "Đăng kí tại đây",
                color = Color.Blue,
                modifier = Modifier.clickable { navController.navigate("register") }
            )
        }

        Divider()

        SignInWithGoogleScreen(viewModel)

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}
