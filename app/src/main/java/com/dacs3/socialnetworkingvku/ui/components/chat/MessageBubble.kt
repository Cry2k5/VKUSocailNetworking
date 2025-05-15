package com.dacs3.socialnetworkingvku.ui.components.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dacs3.socialnetworkingvku.R
import com.dacs3.socialnetworkingvku.data.MessageDTO
import com.dacs3.socialnetworkingvku.ui.components.NavigationBottom
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme

@Composable
fun MessageBubble(message: MessageDTO, currentUser:Long) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.senderId != currentUser) Arrangement.Start else Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if ( message.senderId != currentUser) Color(0xFFF0F0F0) else Color(0xFF007AFF),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = message.content?:"",
                color = if (message.senderId != currentUser) Color.Black else Color.White
            )
        }
    }
}


