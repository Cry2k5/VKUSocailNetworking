package com.dacs3.socialnetworkingvku.ui.screen.profile

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dacs3.socialnetworkingvku.R
import com.dacs3.socialnetworkingvku.data.user.User
import com.dacs3.socialnetworkingvku.data.user.UserDto
import com.dacs3.socialnetworkingvku.data.user.requests.UserUpdateRequest
import com.dacs3.socialnetworkingvku.ui.components.NavigationBottom
import com.dacs3.socialnetworkingvku.ui.components.login_signup.ButtonCustom
import com.dacs3.socialnetworkingvku.ui.components.login_signup.CustomTextField
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme
import com.dacs3.socialnetworkingvku.viewmodel.UploadAvatarState
import com.dacs3.socialnetworkingvku.viewmodel.UploadState
import com.dacs3.socialnetworkingvku.viewmodel.UserViewModel
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
fun ProfileScreen(navController: NavController, userViewModel: UserViewModel) {
    LaunchedEffect(Unit) {
        userViewModel.resetStates()
        userViewModel.getInfo()
        delay(500)
        Log.d("ProfileScreen", "Fetching user info...")
    }

    val context = LocalContext.current
    val userDto by userViewModel.userDtoFlow.collectAsState(initial = UserDto(0, "", "", "", "", "", "", "", ""))

    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf<LocalDate?>(null) }
    var address by remember { mutableStateOf("Chưa cập nhật") }
    var school by remember { mutableStateOf("Chưa cập nhật") }
    var bio by remember { mutableStateOf("Chưa có tiểu sử") }

    LaunchedEffect(userDto) {
        fullName = userDto.username
        phone = userDto.phoneNumber ?: ""
        address = userDto.address?.ifBlank { "Chưa cập nhật" } ?: "Chưa cập nhật"
        school = userDto.school?.ifBlank { "Chưa cập nhật" } ?: "Chưa cập nhật"
        bio = userDto.bio?.ifBlank { "Chưa có tiểu sử" } ?: "Chưa có tiểu sử"
        dateOfBirth = userDto.dateOfBirth?.let {
            runCatching { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) }.onFailure {
                Log.e("ProfileScreen", "Lỗi parse ngày sinh: ${it.message}")
            }.getOrNull()
        }
    }

    val avatarRequest = remember(userDto.avatar) {
        ImageRequest.Builder(context)
            .data(userDto.avatar.takeIf { !it.isNullOrBlank() } ?: R.drawable.avatar_default)
            .crossfade(true)
            .build()
    }

    val uploadState by userViewModel.uploadState.collectAsState()
    val uploadedImageUrl by userViewModel.uploadedImageUrl
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> selectedImageUri = uri }
    )

    val isUpdateSuccess by userViewModel.isUpdateSuccess
    val isDeleteSuccess by userViewModel.isDeleteSuccess
    val isLoading by userViewModel.isLoading

    LaunchedEffect(uploadState) {
        when (uploadState) {
            UploadAvatarState.Loading -> {
                Toast.makeText(context, "Đang upload...", Toast.LENGTH_SHORT).show()
            }
            UploadAvatarState.Success -> {
                Toast.makeText(context, "Upload thành công!", Toast.LENGTH_SHORT).show()
                uploadedImageUrl?.let {
                    userViewModel.updateUser(
                        UserUpdateRequest(
                            name = fullName,
                            address = address,
                            dateOfBirth = dateOfBirth?.format(DateTimeFormatter.ISO_LOCAL_DATE),
                            bio = bio,
                            school = school,
                            avatar = it,
                            phoneNumber = phone
                        )
                    )
                    selectedImageUri = null
                }
            }
            UploadAvatarState.Error -> {
                Toast.makeText(context, "Upload thất bại!", Toast.LENGTH_SHORT).show()
            }
            UploadAvatarState.Idle -> {} // Do nothing
        }

        userViewModel.resetStates() // Reset sau cùng, để tránh Toast bị lặp
    }


    LaunchedEffect(isUpdateSuccess) {
        if (isUpdateSuccess) {
            Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
            userViewModel.resetStates()  // Reset after success
        }
    }

    LaunchedEffect(isDeleteSuccess) {
        if (isDeleteSuccess) {
            Toast.makeText(context, "Xóa tài khoản thành công!", Toast.LENGTH_SHORT).show()
            navController.navigate("login")
            userViewModel.resetStates()
        }
    }

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
            val imagePainter = if (selectedImageUri != null) {
                rememberAsyncImagePainter(selectedImageUri)
            } else {
                rememberAsyncImagePainter(avatarRequest)
            }

            Image(
                painter = imagePainter,
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .clickable { pickImageLauncher.launch("image/*") }
            )
            Spacer(modifier = Modifier.height(24.dp))

            CustomTextField(value = fullName, onValueChange = { fullName = it }, label = "Họ và tên")
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(value = phone, onValueChange = { phone = it }, label = "Số điện thoại", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                value = dateOfBirth?.toString().orEmpty(),
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
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(value = address, onValueChange = { address = it }, label = "Địa chỉ")
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(value = school, onValueChange = { school = it }, label = "Trường học")
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(value = bio, onValueChange = { bio = it }, label = "Tiểu sử")
            Spacer(modifier = Modifier.height(24.dp))

            ButtonCustom(text = "Cập nhật", onClick = {
                if (fullName.isBlank() || dateOfBirth == null ||
                    address.isBlank() || phone.isBlank() || school.isBlank()
                ) {
                    Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                    return@ButtonCustom
                }
                if (selectedImageUri != null) {
                    userViewModel.uploadImage(selectedImageUri!!, context)
                } else {
                    userViewModel.updateUser(
                        UserUpdateRequest(
                            name = fullName,
                            address = address,
                            dateOfBirth = dateOfBirth?.format(DateTimeFormatter.ISO_LOCAL_DATE),
                            bio = bio,
                            school = school,
                            avatar = userDto.avatar,
                            phoneNumber = phone
                        )
                    )
                }
            })
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    if (!isLoading) userViewModel.deleteAccount()
                },
                enabled = !isLoading,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Xóa tài khoản")
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
