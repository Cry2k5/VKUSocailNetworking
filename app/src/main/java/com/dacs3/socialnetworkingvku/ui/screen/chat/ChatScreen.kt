package com.dacs3.socialnetworkingvku.ui.screen.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dacs3.socialnetworkingvku.ui.components.Chat_CommentCustom
import com.dacs3.socialnetworkingvku.ui.components.NavigationBottom
import com.dacs3.socialnetworkingvku.ui.components.SearchBar
import com.dacs3.socialnetworkingvku.ui.components.TopAppBarHeader
import com.dacs3.socialnetworkingvku.ui.components.chat.MessageItem
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme
import com.dacs3.socialnetworkingvku.data.Message
import com.dacs3.socialnetworkingvku.ui.components.chat.MessageBubble

@Composable
fun ChatScreen(
    userName: String = "Hứa Huỳnh Anh",
    onBackClick: () -> Unit = {}
) {
    var messageText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBarHeader(
                content = userName,
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        },
        bottomBar = {
            Chat_CommentCustom(
                text = messageText,
                onTextChange = { messageText = it },
                onSendClick = {
                    // Gửi tin nhắn
                    messageText = ""
                },
                leadingIcons = {
                    IconButton(onClick = { /* File action */ }) {
                        Icon(Icons.Default.AttachFile, contentDescription = "File")
                    }
                    IconButton(onClick = { /* Attach image */ }) {
                        Icon(Icons.Default.Image, contentDescription = "Hình ảnh")
                    }
                    IconButton(onClick = { /* Emoji or sticker */ }) {
                        Icon(Icons.Default.EmojiEmotions, contentDescription = "Sticker")
                    }
                }
            )
        }
    ) { innerPadding ->
        val messages = listOf(
            Message("Hey Chiến!", true),
            Message("Hả?", false),
            Message("Đánh cầu lông không?", true),
            Message("Không!!!", false)
        )

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                MessageBubble(message)
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatScreenPreview() {
    VKUSocialNetworkingTheme {
        ChatScreen()
    }
}
