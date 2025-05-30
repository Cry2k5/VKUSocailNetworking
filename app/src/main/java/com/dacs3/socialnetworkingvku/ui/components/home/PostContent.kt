package com.dacs3.socialnetworkingvku.ui.components.home

import android.net.Uri
import android.util.Log
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme

@Composable
fun PostContent(content: String, imgContent: String?) {
    Column {
        Text(text = content)
        Spacer(modifier = Modifier.height(8.dp))

        // Chỉ hiển thị ảnh nếu imgContent không null hoặc rỗng
        if (!imgContent.isNullOrBlank()) {
            Log.d("PostContent",imgContent)
            AsyncImage(
                model = imgContent,
                contentDescription = null,
                contentScale = ContentScale.Fit, // hoặc ContentScale.Inside nếu muốn an toàn hơn
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp) // giới hạn chiều cao tối đa
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

    }
}
