package com.dacs3.socialnetworkingvku.ui.screen.login_signup

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dacs3.socialnetworkingvku.R
import com.dacs3.socialnetworkingvku.ui.components.login_signup.CustomTextField
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme
import com.dacs3.socialnetworkingvku.viewmodel.AuthViewModel

@Composable
fun AccountVerificationScreen(viewModel: AuthViewModel, navController: NavController) {
    val scrollState = rememberScrollState()
    var otp by remember { mutableStateOf("") }
    val context = LocalContext.current
    // Lấy giá trị từ ViewModel
    val isLoading by viewModel.isLoading
    val isSuccess by viewModel.isSuccess
    val errorMessage by viewModel.errorMessage
    val registerData = viewModel.pendingRegisterData

    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            viewModel.resetStates()
            navController.navigate("login")
            {
                popUpTo("verify_otp") { inclusive = true }
            }
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Quay lại",
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable {
                    navController.popBackStack()
                }
        )

        Text(
            text = "Xác nhận tài khoản",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Text(
            text = "Chúng tôi sẽ gửi mã xác nhận đến email bạn đã nhập.",
            fontSize = 14.sp,
            color = Color.Gray
        )

        // Nhập mã xác thực
        CustomTextField(
            value = otp,
            onValueChange = { otp = it },
            label = "Nhập mã xác nhận",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Button(
            onClick = {
                if(otp.isBlank()){
                    Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (registerData != null) {
                    registerData.dateOfBirth?.let {
                        viewModel.verifyOtp(registerData.username,registerData.email,registerData.address,otp,registerData.password,
                            it, registerData.phone,registerData.school)
                    }
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF))
        ) {
            Text("Tiếp tục", color = Color.White)
        }

        Text(
            text = "Gửi lại mã",
            color = Color.Black,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                }
        )

        Spacer(modifier = Modifier.height(32.dp))
        Divider()

        OutlinedButton(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = "Google",
                modifier = Modifier.size(20.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Tiếp tục với Google")
        }

        Spacer(modifier = Modifier.height(24.dp))
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }


}

