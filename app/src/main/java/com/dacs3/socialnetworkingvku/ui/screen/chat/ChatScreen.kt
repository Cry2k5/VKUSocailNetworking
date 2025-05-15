package com.dacs3.socialnetworkingvku.ui.screen.chat

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dacs3.socialnetworkingvku.ui.components.Chat_CommentCustom
import com.dacs3.socialnetworkingvku.ui.components.TopAppBarHeader
import com.dacs3.socialnetworkingvku.data.message.MessageDTO
import com.dacs3.socialnetworkingvku.repository.ChatRepository
import com.dacs3.socialnetworkingvku.security.TokenStoreManager
import com.dacs3.socialnetworkingvku.ui.components.chat.MessageBubble
import com.dacs3.socialnetworkingvku.viewmodel.ChatViewModel
import com.dacs3.socialnetworkingvku.websocket.WebSocketManager
import java.time.LocalDateTime
import com.dacs3.socialnetworkingvku.data.message.toMessageDTO

@Composable
fun ChatScreen(
    currentUserId: Long,
    receiverId: Long,
    receiverName: String,
    chatViewModel: ChatViewModel,
    onBackClick: () -> Unit = {}
) {
    val messagesMap by chatViewModel.messages.observeAsState(emptyMap())
    val receiverMessages = messagesMap[receiverId] ?: emptyList()

    val socketMessages = remember(receiverId) { mutableStateListOf<MessageDTO>() }
    val socketManager = remember(receiverId) {
        WebSocketManager(currentUserId) { incoming ->
            socketMessages.add(incoming)
        }.apply { connect() }
    }

    var messageText by remember { mutableStateOf("") }

    // Trigger fetching when receiver changes
    LaunchedEffect(receiverId) {
        socketMessages.clear()
        chatViewModel.getMessages(receiverId)
    }

    DisposableEffect(receiverId) {
        onDispose {
            socketManager.disconnect()
            chatViewModel.clearMessages(receiverId)
        }
    }

    val allMessages = (receiverMessages.map { it.toMessageDTO() } + socketMessages)
        .sortedBy { it.createAt }

    Scaffold(
        topBar = {
            TopAppBarHeader(
                content = receiverName,
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
                    val newMsg = MessageDTO(
                        senderId = currentUserId,
                        receiverId = receiverId,
                        content = messageText,
                        image = null,
                        video = null,
                        createAt = LocalDateTime.now().toString()
                    )
                    socketManager.sendMessage(newMsg)
                    socketMessages.add(newMsg)
                    messageText = ""
                },
                leadingIcons = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.AttachFile, contentDescription = "File")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Image, contentDescription = "Hình ảnh")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.EmojiEmotions, contentDescription = "Sticker")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(allMessages) { message ->
                MessageBubble(message, currentUserId, receiverId)
            }
        }
    }
}

