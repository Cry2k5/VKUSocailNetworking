package com.dacs3.socialnetworkingvku.ui.screen.login_signup

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dacs3.socialnetworkingvku.data.auth.requests.RegisterRequest
import com.dacs3.socialnetworkingvku.ui.components.login_signup.SignInWithGoogleScreen
import com.dacs3.socialnetworkingvku.ui.components.login_signup.ButtonCustom
import com.dacs3.socialnetworkingvku.ui.components.login_signup.CustomTextField
import com.dacs3.socialnetworkingvku.viewmodel.AuthViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
fun RegisterScreen(navController: NavController, viewModel: AuthViewModel) {
    val isLoading by viewModel.isLoading
    val isSuccess by viewModel.isSuccess
    val errorMessage by viewModel.errorMessage

    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf<LocalDate?>(null) }
    var address by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var school by remember { mutableStateOf("") }

    val googleLoginSuccess by viewModel.googleLoginSuccess
    val googleLoginErrorMessage by viewModel.googleLoginErrorMessage
    // Điều hướng sang OTP khi đăng ký thành công
    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            viewModel.resetStates()
            Log.d("RegisterScreen", "Navigating to verify_otp screen")
            navController.navigate("verify_otp") {
                popUpTo("register") { inclusive = true }
            }
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(googleLoginSuccess) {
        when (googleLoginSuccess) {
            true -> {
                Toast.makeText(context, "Đăng nhập Google thành công!", Toast.LENGTH_SHORT).show()
                viewModel.resetGoogleLoginState()
                navController.navigate("home") {
                    popUpTo("register") { inclusive = true }
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
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                Icons.Default.ArrowBack, contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Đăng kí", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }

        // Input fields
        CustomTextField(value = username, onValueChange = { username = it }, label = "Họ và Tên")
        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        CustomTextField(value = address, onValueChange = { address = it }, label = "Địa chỉ")

        // Ngày sinh
        CustomTextField(
            value = dateOfBirth?.toString() ?: "",
            onValueChange = {},
            label = "Ngày sinh",
            trailingIcon = Icons.Default.CalendarToday,
            onTrailingIconClick = {
                val calendar = Calendar.getInstance()
                android.app.DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        dateOfBirth = LocalDate.of(year, month + 1, dayOfMonth)
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        )

        CustomTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = "Số điện thoại",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            visualTransformation = PasswordVisualTransformation()
        )
        CustomTextField(
            value = school,
            onValueChange = { school = it },
            label = "Nhập tên trường"
        )

        // Button
        ButtonCustom(
            onClick = {
                // Validate
                if (username.isBlank() || email.isBlank() || dateOfBirth == null ||
                    address.isBlank() || phoneNumber.isBlank() || password.isBlank() || school.isBlank()
                ) {
                    Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                    return@ButtonCustom
                }

                val request = RegisterRequest(
                    username = username,
                    email = email,
                    address = address,
                    dateOfBirth = dateOfBirth!!.format(DateTimeFormatter.ISO_LOCAL_DATE),
                    phone = phoneNumber,
                    password = password,
                    school = school
                )
                viewModel.cacheRegisterData(request)
                viewModel.register(request)
            },
            text = "Đăng kí ngay"
        )

        Divider()
        SignInWithGoogleScreen(viewModel)

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

