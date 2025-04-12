package com.dacs3.socialnetworkingvku.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dacs3.socialnetworkingvku.R
import com.dacs3.socialnetworkingvku.ui.components.Chat_CommentCustom
import com.dacs3.socialnetworkingvku.ui.components.TopAppBarHeader
import com.dacs3.socialnetworkingvku.ui.components.home.PostItem
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen() {
    var commentText by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()) {
        // Top App Bar
        TopAppBarHeader(

            content ="Comment",
            navigationIcon = {
                IconButton(onClick = { /* TODO: Xử lý quay lại */ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )

        Divider()

        PostItem(
            username = "Hứa Huỳnh Anh",
            date = "October 11",
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc enim, porttitor",
            imageRes = R.drawable.demo_image_background,
            stats = "😍😢❤️ 177    42 bình luận  5 lượt chia sẻ"
        )

        Divider()

        // Danh sách bình luận
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(listOf("Đẹp ghê", "Hahaha")) { comment ->
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Huỳnh Anh", fontWeight = FontWeight.Bold)
                        Surface(
                            color = Color(0xFFF0F0F0),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.padding(top = 2.dp)
                        ) {
                            Text(
                                comment,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }

        Divider()

       Chat_CommentCustom(
           text = commentText,
           onTextChange = { commentText = it },
           onSendClick = {
               // TODO: Gửi comment
               println("Đã gửi: $commentText")
               commentText = ""
           }
       )
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PostDetailPreview() {
    VKUSocialNetworkingTheme {
        PostDetailScreen()
    }
}
