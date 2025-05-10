package com.dacs3.socialnetworkingvku.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.dacs3.socialnetworkingvku.ui.components.NavigationBottom
import com.dacs3.socialnetworkingvku.ui.components.login_signup.ButtonCustom
import com.dacs3.socialnetworkingvku.ui.components.login_signup.CustomTextField
import com.dacs3.socialnetworkingvku.ui.screen.notification.NotificationScreen
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme
import com.dacs3.socialnetworkingvku.viewmodel.UserViewModel

@Composable
fun ProfileScreen(navController: NavController, userViewModel: UserViewModel) {
    var nickname by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var school by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = { NavigationBottom(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Cá nhân", fontSize = 20.sp, color = Color.Black)

            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painter = rememberAsyncImagePainter("https://cdn-icons-png.flaticon.com/512/706/706830.png"),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text("chientq@23itb@vku.udn.vn", fontSize = 14.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(24.dp))
            CustomTextField(value = nickname, onValueChange = { nickname = it }, label = "Biệt danh")
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(value = fullName, onValueChange = { fullName = it }, label = "Họ và tên")
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(value = phone, onValueChange = { phone = it }, label = "Số điện thoại", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(value = birthday, onValueChange = { birthday = it }, label = "Ngày sinh", trailingIcon = Icons.Default.CalendarToday)
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(value = address, onValueChange = { address = it }, label = "Địa chỉ")
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(value = school, onValueChange = { school = it }, label = "Trường học")
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(value = bio, onValueChange = { bio = it }, label = "Tiểu sử")
            Spacer(modifier = Modifier.height(24.dp))

            ButtonCustom(text = "Cập nhật thông tin", onClick = { /* TODO */ })
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { /* TODO: Xử lý xóa tài khoản */ },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Xóa tài khoản")
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

