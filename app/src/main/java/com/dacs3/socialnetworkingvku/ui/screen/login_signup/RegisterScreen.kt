package com.dacs3.socialnetworkingvku.ui.screen.login_signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dacs3.socialnetworkingvku.ui.components.login_signup.SignInWithGoogleScreen
import com.dacs3.socialnetworkingvku.ui.components.shared.ButtonCustom
import com.dacs3.socialnetworkingvku.ui.components.shared.CustomTextField
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme

@Composable
fun RegisterScreen(onBackClick: () -> Unit = {}) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var password_repeat by remember { mutableStateOf("") }



    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back",
                modifier = Modifier.size(24.dp).clickable { onBackClick() })
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Đăng kí", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }

        CustomTextField(value = fullName, onValueChange = { fullName = it }, label = "Họ và Tên")
        CustomTextField(value = email, onValueChange = { email = it }, label = "Email", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
        CustomTextField(value = birthDate, onValueChange = { birthDate = it }, label = "Ngày sinh", trailingIcon = Icons.Default.CalendarToday)
        CustomTextField(value = address, onValueChange = { address = it }, label = "Địa chỉ")
        CustomTextField(value = phoneNumber, onValueChange = { phoneNumber = it }, label = "Số điện thoại", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))
        CustomTextField(value = password, onValueChange = { password = it }, label = "Password", visualTransformation = PasswordVisualTransformation())
        CustomTextField(value = password_repeat, onValueChange = { password_repeat = it }, label = "Nhập lại password", visualTransformation = PasswordVisualTransformation())

        ButtonCustom(
            onClick = { },
            text = "Đăng kí ngay"
        )

        Divider()

        SignInWithGoogleScreen()
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    VKUSocialNetworkingTheme {
        RegisterScreen()
    }
}
