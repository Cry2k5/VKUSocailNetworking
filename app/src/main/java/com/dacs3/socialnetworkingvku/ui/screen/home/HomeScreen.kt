package com.dacs3.socialnetworkingvku.ui.screen.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
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
import com.dacs3.socialnetworkingvku.ui.components.*
import com.dacs3.socialnetworkingvku.ui.components.home.PostItem
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = { SearchBar(
            content = "Tìm kiếm...",
            true
        ) },
        bottomBar = { NavigationBottom() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(2) {
                PostItem(
                    username = "Hứa Huỳnh Anh",
                    date = "October 11",
                    content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc enim, porttitor",
                    imageRes = R.drawable.demo_image_background,
                    stats = "😍😢❤️ 177    42 bình luận  5 lượt chia sẻ"
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    VKUSocialNetworkingTheme {
        HomeScreen()
    }
}
