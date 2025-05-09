import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
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
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.dacs3.socialnetworkingvku.viewmodel.PostViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CreatePostScreen(
    viewModel: PostViewModel,
    controller: NavController,
    context: Context
) {
    var postContent by remember { mutableStateOf(TextFieldValue("")) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var uploadedImageUrl by remember { mutableStateOf<String?>(null) }
    val isLoading by viewModel.isLoading
    val isSuccess by viewModel.isSuccess
    val errorMessage by viewModel.errorMessage
    // Register the launcher for picking an image
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            selectedImageUri = uri
        }
    )

    LaunchedEffect(isSuccess) {
        Log.d("LoginScreen", "isSuccess: $isSuccess")
        if (isSuccess) {
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
            .padding(16.dp)
    ) {
        Text(
            text = "Tạo bài viết",
            style = MaterialTheme.typography.headlineSmall
        )

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

        // Ảnh được chọn
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

        // Nút chọn ảnh
        TextButton(
            onClick = {
                // Mở màn hình chọn ảnh
                pickImageLauncher.launch("image/*")
            }
        ) {
            Icon(Icons.Default.Image, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Thêm ảnh")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Nút hành động
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = { controller.popBackStack() } // Quay lại màn hình trước
            ) {
                Text("Hủy")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (selectedImageUri != null) {
                        // Upload ảnh lên Cloudinary
                        CoroutineScope(Dispatchers.IO).launch {
                            val imageUrl = viewModel.uploadImageToCloudinary(selectedImageUri!!, context)
                            if (imageUrl != null) {
                                // Lưu URL ảnh từ Cloudinary
                                uploadedImageUrl = imageUrl
                                // Tạo bài viết với URL ảnh đã upload
                                viewModel.createPost(postContent.text, uploadedImageUrl)
                            }
                        }
                    } else {
                        // Nếu không có ảnh, chỉ đăng bài với nội dung
                        viewModel.createPost(postContent.text, null)
                    }
                },
                enabled = postContent.text.isNotBlank()
            ) {
                Text("Đăng")
            }
        }
    }
}
