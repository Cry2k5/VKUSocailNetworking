package com.dacs3.socialnetworkingvku.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarHeader(
    content: String,
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable (() -> Unit))? = null,
    actionIcons: @Composable RowScope.() -> Unit = {} // Cho nhiều action phía sau
) {
    androidx.compose.material3.TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = content,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        },
        navigationIcon = {
            navigationIcon?.invoke()
        },
        actions = actionIcons
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TopAppBarPreview() {
    VKUSocialNetworkingTheme {
        TopAppBarHeader("Content")
    }
}
