package com.dacs3.socialnetworkingvku.ui.screen.login_signup

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dacs3.socialnetworkingvku.ui.components.TopAppBarHeader
import com.dacs3.socialnetworkingvku.ui.components.login_signup.CustomTextField
import com.dacs3.socialnetworkingvku.viewmodel.AuthViewModel

@Composable
fun ForgotPasswordScreen(viewModel: AuthViewModel, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var otpSent by remember { mutableStateOf(false) }
    var otp by remember { mutableStateOf("") }

    val context = LocalContext.current
    val isLoading by viewModel.isLoading
    val isForgotSuccess by viewModel.isForgotSuccess
    val forgotErrorMessage by viewModel.forgotErrorMessage
    val isOtpVerified by viewModel.isOtpVerified
    val otpErrorMessage by viewModel.otpErrorMessage

    // Gửi OTP thành công
    LaunchedEffect(isForgotSuccess, forgotErrorMessage) {
        forgotErrorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.resetStates()
        }

        if (isForgotSuccess) {
            Toast.makeText(context, "Đã gửi mã OTP", Toast.LENGTH_SHORT).show()
            otpSent = true
            viewModel.resetStates()
        }
    }

    // Xác minh OTP thành công
    LaunchedEffect(isOtpVerified, otpErrorMessage) {
        otpErrorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.resetStates()
        }

        if (isOtpVerified) {
            Toast.makeText(context, "Xác minh thành công", Toast.LENGTH_SHORT).show()
            viewModel.resetStates()
            navController.navigate("login") {
                popUpTo("forgot_password") { inclusive = true }
            }
        }
    }

    // Column để chứa UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Thêm TopAppBar
        TopAppBarHeader(
            content = "Quên mật khẩu",
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )

        Text(
            text = "Chúng tôi sẽ gửi mã xác nhận đến email bạn đã nhập.",
            fontSize = 14.sp,
            color = Color.Gray
        )

        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "Nhập email",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        if (!otpSent) {
            Button(
                onClick = {
                    if (email.isBlank()) {
                        Toast.makeText(context, "Vui lòng nhập email", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    viewModel.forgotPassword(email)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF))
            ) {
                Text("Gửi mã xác nhận", color = Color.White)
            }
        } else {
            CustomTextField(
                value = otp,
                onValueChange = { otp = it },
                label = "Nhập mã xác nhận",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Text(
                text = "Gửi lại mã",
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        if (!isLoading) {
                            viewModel.forgotPassword(email)
                        }
                    }
            )

            Button(
                onClick = {
                    if (otp.isBlank()) {
                        Toast.makeText(context, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    viewModel.verifyOtpPassword(email, otp)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF))
            ) {
                Text("Tiếp tục", color = Color.White)
            }
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}
