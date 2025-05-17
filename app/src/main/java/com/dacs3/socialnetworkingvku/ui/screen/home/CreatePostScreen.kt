@file:OptIn(ExperimentalMaterial3Api::class)

package com.dacs3.socialnetworkingvku.ui.screen.home

import GeminiContent
import GeminiPart
import GeminiRequest
import InlineData
import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.dacs3.socialnetworkingvku.testApi.RetrofitClient
import com.dacs3.socialnetworkingvku.viewmodel.GeminiViewModel
import com.dacs3.socialnetworkingvku.viewmodel.PostViewModel
import com.dacs3.socialnetworkingvku.viewmodel.UploadState

@Composable
fun CreatePostScreen(
    postViewModel: PostViewModel,
    navController: NavController,
    geminiViewModel: GeminiViewModel,
    context: Context
) {
    var postContent by remember { mutableStateOf(TextFieldValue("")) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var aiCaption by remember { mutableStateOf<String?>(null) }

    val uploadState by postViewModel.uploadState.collectAsState()
    val uploadedImageUrl by postViewModel.uploadedImageUrl
    val isSuccess by postViewModel.isSuccess
    val errorMessage by postViewModel.errorMessage
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            selectedImageUri = uri
            aiCaption = "Đang tạo nội dung với AI..."
        }
    )

    // Khi có sự thay đổi về ảnh, gọi đến ViewModel để xử lý API
    LaunchedEffect(selectedImageUri) {
        selectedImageUri?.let { uri ->
            try {
                val base64Image = convertImageUriToBase64(uri, context)
                val request = GeminiRequest(
                    contents = listOf(
                        GeminiContent(
                            parts = listOf(
                                GeminiPart(
                                    inline_data = InlineData(
                                        mime_type = "image/jpeg",
                                        data = base64Image
                                    )
                                ),
                                GeminiPart(
                                    text = "Viết một đoạn caption ngắn gọn, súc tích (dưới 30 từ) dựa vào ảnh trên. Dành cho bài viết mạng xã hội."
                                )
                            )
                        )
                    )
                )

                // Gọi đến ViewModel để lấy phản hồi từ API
                geminiViewModel.generateContent(request)
            } catch (e: Exception) {
                aiCaption = "Không thể kết nối đến AI"
                e.printStackTrace()
            }
        }
    }

    // Khi có dữ liệu trả về từ AI, cập nhật aiCaption
    val aiResponse = geminiViewModel.aiContent.value
    aiResponse?.let {
        aiCaption = it
    }

    fun showToast(msg: String) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    LaunchedEffect(uploadState) {
        when (uploadState) {
            UploadState.Success -> {
                uploadedImageUrl?.let {
                    postViewModel.createPost(postContent.text, it)
                }
                postViewModel.resetState()
            }
            UploadState.Error -> showToast("Tải ảnh thất bại")
            else -> {}
        }
    }

    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            postViewModel.resetState()
            navController.navigate("home") {
                popUpTo("create_post") { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Tạo bài viết") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.Close, contentDescription = "Đóng")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Người dùng", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "Chỉ mình tôi ⌄",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.clickable {
                            showToast("Tính năng chưa phát triển")
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Text field
            OutlinedTextField(
                value = postContent,
                onValueChange = { postContent = it },
                placeholder = { Text("Bạn đang nghĩ gì?") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 150.dp),
                maxLines = 10,
                textStyle = MaterialTheme.typography.bodyLarge,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Image + AI caption
            selectedImageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Ảnh",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                aiCaption?.let {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (it == "Đang tạo nội dung với AI...") {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(end = 6.dp),
                                strokeWidth = 2.dp
                            )
                        }
                        Text(
                            text = "🧠 Gợi ý: $it",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    TextButton(
                        onClick = {
                            postContent = TextFieldValue(
                                postContent.text + if (postContent.text.endsWith(" ")) it else " $it"
                            )
                            aiCaption = null
                        }
                    ) {
                        Text("Dùng gợi ý này")
                    }
                }

                TextButton(
                    onClick = {
                        selectedImageUri = null
                        aiCaption = null
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Xóa ảnh", color = MaterialTheme.colorScheme.error)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Divider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PostOption(icon = Icons.Default.Image, text = "Ảnh/Video") {
                    pickImageLauncher.launch("image/*")
                }
                PostOption(icon = Icons.Default.Person, text = "Gắn thẻ") {
                    showToast("Tính năng chưa phát triển")
                }
                PostOption(icon = Icons.Default.EmojiEmotions, text = "Cảm xúc") {
                    showToast("Tính năng chưa phát triển")
                }
                PostOption(icon = Icons.Default.Place, text = "Check-in") {
                    showToast("Tính năng chưa phát triển")
                }
            }

            Divider()

            Spacer(modifier = Modifier.weight(1f))

            // Submit button
            Button(
                onClick = {
                        postContent = TextFieldValue(postContent.text)
                    if (selectedImageUri != null) {
                        postViewModel.uploadImage(selectedImageUri!!, context)
                    } else {
                        postViewModel.createPost(postContent.text, null)
                    }
                },
                enabled = postContent.text.isNotBlank(),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                if (uploadState == UploadState.Loading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 8.dp)
                    )
                }
                Text("Đăng")
            }
        }
    }
}

@Composable
fun PostOption(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = text, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}
fun convertImageUriToBase64(uri: Uri, context: Context): String {
    return context.contentResolver.openInputStream(uri)?.use {
        val bytes = it.readBytes()
        Base64.encodeToString(bytes, Base64.NO_WRAP)
    } ?: ""
}
