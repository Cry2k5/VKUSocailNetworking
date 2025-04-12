package com.dacs3.socialnetworkingvku.ui.screen.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dacs3.socialnetworkingvku.ui.components.NavigationBottom
import com.dacs3.socialnetworkingvku.ui.components.SearchBar
import com.dacs3.socialnetworkingvku.ui.components.TopAppBarHeader
import com.dacs3.socialnetworkingvku.ui.components.chat.MessageItem
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme

@Composable
fun MessageScreen() {
    Scaffold(
        topBar = {
            TopAppBarHeader(content = "Nhắn tin")
        },
        bottomBar = {
            NavigationBottom()
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ) {
            SearchBar("Tìm kiếm...")

            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(8) {
                    MessageItem(
                        name = "Hứa Huỳnh Anh",
                        message = if (it == 0) "Hey Chiến!" else "Chào bạn, mình tên là Anh, rất vui được gặp bạn!",
                        unreadCount = 9
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MessagePreview() {
    VKUSocialNetworkingTheme {
        MessageScreen()
    }
}