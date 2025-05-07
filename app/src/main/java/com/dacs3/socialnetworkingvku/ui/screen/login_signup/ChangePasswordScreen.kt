package com.dacs3.socialnetworkingvku.ui.screen.login_signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme

@Composable
fun ChangePasswordScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Icon back
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Quay lại",
            modifier = Modifier
                .clickable { /* TODO: Handle back */ }
                .padding(top = 8.dp)
        )

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
            value = "",
            onValueChange = {},
            placeholder = { Text("Nhập mật khẩu mới") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        // Nhập lại mật khẩu mới
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Nhập lại mật khẩu mới") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        // Nút đổi mật khẩu
        Button(
            onClick = { /* TODO: Đổi mật khẩu */ },
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
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChangePasswordScreenPreview() {
    VKUSocialNetworkingTheme {
        ChangePasswordScreen()
    }
}