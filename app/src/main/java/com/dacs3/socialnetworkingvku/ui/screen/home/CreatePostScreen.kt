import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    onPostSubmit: (String, Uri?) -> Unit,
    onCancel: () -> Unit
) {
    var postContent by remember { mutableStateOf(TextFieldValue("")) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

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

        // Nút chọn ảnh (giả lập chọn ảnh cho preview/demo)
        TextButton(
            onClick = {
                // Giả lập URI ảnh — trong app thực, bạn sẽ gọi image picker
                selectedImageUri = Uri.parse("https://via.placeholder.com/600x400.png?text=Ảnh+chọn")
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
            TextButton(onClick = onCancel) {
                Text("Hủy")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (postContent.text.isNotBlank()) {
                        onPostSubmit(postContent.text.trim(), selectedImageUri)
                    }
                },
                enabled = postContent.text.isNotBlank()
            ) {
                Text("Đăng")
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewCreatePostScreen() {
    CreatePostScreen(
        onPostSubmit = { content, imageUri -> println("Nội dung: $content, Ảnh: $imageUri") },
        onCancel = { println("Hủy bài đăng") }
    )
}
