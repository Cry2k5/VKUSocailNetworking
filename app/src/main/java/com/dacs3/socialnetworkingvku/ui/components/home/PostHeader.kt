package com.dacs3.socialnetworkingvku.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.dacs3.socialnetworkingvku.R
import com.dacs3.socialnetworkingvku.ui.components.Chat_CommentCustom
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@Composable
fun PostHeader(username: String, date: String, imgAvatar: String?) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // Nếu avatar không null và không rỗng, tải ảnh từ URL, nếu không thì sử dụng ảnh mặc định
        val avatarPainter = rememberAsyncImagePainter(
            model = imgAvatar?.takeIf { it.isNotEmpty() } ?: R.drawable.avatar_default
        )

        Image(
            painter = avatarPainter,
            contentDescription = "Avatar",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(username, fontWeight = FontWeight.Bold)
            Text(formatDateTime(date), fontSize = 12.sp, color = Color.Gray)
        }
    }
}

fun formatDateTime(isoString: String): String {
    return try {
        val formatterInput = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val localDateTime = java.time.LocalDateTime.parse(isoString, formatterInput)
        val formatterOutput = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        formatterOutput.format(localDateTime)
    } catch (e: Exception) {
        "Không xác định"
    }
}
