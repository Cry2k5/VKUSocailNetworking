package com.dacs3.socialnetworkingvku.ui.screen.home
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.dacs3.socialnetworkingvku.viewmodel.PostViewModel
import com.dacs3.socialnetworkingvku.viewmodel.UploadState

@Composable
fun CreatePostScreen(
    postViewModel: PostViewModel,
    controller: NavController,
    context: Context
) {
    var postContent by remember { mutableStateOf(TextFieldValue("")) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val uploadState by postViewModel.uploadState.collectAsState()
    val uploadedImageUrl by postViewModel.uploadedImageUrl
    val isSuccess by postViewModel.isSuccess
    val errorMessage by postViewModel.errorMessage

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> selectedImageUri = uri }
    )

    // Hiển thị thông báo trạng thái upload
    LaunchedEffect(uploadState) {
        when (uploadState) {
            UploadState.Loading -> Unit
            UploadState.Success -> {
                Toast.makeText(context, "Upload thành công!", Toast.LENGTH_SHORT).show()
                uploadedImageUrl?.let {
                    postViewModel.createPost(postContent.text, it)
                }
                postViewModel.resetState()
            }
            UploadState.Error -> {
                Toast.makeText(context, "Upload thất bại!", Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    // Khi post thành công thì chuyển trang
    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            postViewModel.resetState()
            controller.navigate("home") {
                popUpTo("create_post") { inclusive = true }
            }
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding() // Tránh bị che bởi status bar
            .padding(16.dp)
    ) {
        Text("Tạo bài viết", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = postContent,
            onValueChange = { postContent = it },
            placeholder = { Text("Bạn đang nghĩ gì?") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp, max = 250.dp),
            maxLines = 10,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        selectedImageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Ảnh đã chọn",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }

        TextButton(onClick = { pickImageLauncher.launch("image/*") }) {
            Icon(Icons.Default.Image, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Thêm ảnh")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = {
                postViewModel.resetState()
                controller.popBackStack()
            }) {
                Text("Hủy")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (selectedImageUri != null) {
                        postViewModel.uploadImage(selectedImageUri!!, context)
                    } else {
                        postViewModel.createPost(postContent.text, null)
                    }
                },
                enabled = postContent.text.isNotBlank()
            ) {
                if (uploadState == UploadState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(18.dp)
                            .padding(end = 8.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                }
                Text("Đăng")
            }
        }
    }
}
