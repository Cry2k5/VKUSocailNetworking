package com.dacs3.socialnetworkingvku.ui.components.chat

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dacs3.socialnetworkingvku.data.message.MessageDTO

@Composable
fun MessageBubble(message: MessageDTO, currentUserId:Long, currentReceivedId:Long) {
    val isFromFriend = message.senderId != currentUserId
//    Log.d("MessageBubble", "isFromFriend: $isFromFriend")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isFromFriend) Arrangement.Start else Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (isFromFriend) Color(0xFFF0F0F0) else Color(0xFF007AFF),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = message.content?:"",
                color = if (isFromFriend) Color.Black else Color.White
            )
        }
    }
}


