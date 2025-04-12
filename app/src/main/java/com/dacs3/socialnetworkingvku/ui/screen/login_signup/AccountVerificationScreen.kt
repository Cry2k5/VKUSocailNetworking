package com.dacs3.socialnetworkingvku.ui.screen.login_signup

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dacs3.socialnetworkingvku.R
import com.dacs3.socialnetworkingvku.ui.components.shared.CustomTextField
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme

@Composable
fun AccountVerificationScreen(onBackClick: () -> Unit = {}) {
    val scrollState = rememberScrollState()
    var verificationCode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Nút quay lại
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Quay lại",
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable { onBackClick() }
        )

        // Tiêu đề
        Text(
            text = "Xác nhận tài khoản",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        // Mô tả
        Text(
            text = "Chúng tôi đã gửi mã đến thiết bị mà bạn đang dùng để đăng nhập.",
            fontSize = 14.sp,
            color = Color.Gray
        )

        // Nhập mã xác thực (sử dụng CustomTextField)
        CustomTextField(
            value = verificationCode,
            onValueChange = { verificationCode = it },
            label = "Nhập mã",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Nút tiếp tục
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF))
        ) {
            Text("Tiếp tục", color = Color.White)
        }

        // Gửi lại mã
        Text(
            text = "Gửi lại mã",
            color = Color.Black,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable { /* TODO: Gửi lại mã */ }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Divider()

        // Google login button
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
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AccountVerificationPreview() {
    VKUSocialNetworkingTheme {
        AccountVerificationScreen()
    }
}
