// LoginScreen.kt
package com.dacs3.socialnetworkingvku.ui.screen.login_signup

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
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dacs3.socialnetworkingvku.ui.components.login_signup.SignInWithGoogleScreen
import com.dacs3.socialnetworkingvku.ui.components.shared.ButtonCustom
import com.dacs3.socialnetworkingvku.ui.components.shared.CustomTextField
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Mạng xã hội,\nkết nối đam mê.",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 36.sp
        )

        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email"
        )

        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Mật khẩu",
            visualTransformation = PasswordVisualTransformation()
        )

        Text(
            text = "Quên mật khẩu",
            color = Color.Blue,
            modifier = Modifier.align(Alignment.End).clickable { }
        )

        ButtonCustom(
            text = "Đăng nhập",
            onClick = {}
            )

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text("Không có tài khoản? ")
            Text("Đăng kí tại đây", color = Color.Blue, modifier = Modifier.clickable { })
        }

        Divider()

        SignInWithGoogleScreen()
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    VKUSocialNetworkingTheme {
        LoginScreen()
    }
}

