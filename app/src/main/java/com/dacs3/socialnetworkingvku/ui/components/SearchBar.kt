package com.dacs3.socialnetworkingvku.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(content: String, hasStatusBarPadding: Boolean = false) {
    val modifier = Modifier
        .fillMaxWidth()
        .then(if (hasStatusBarPadding) Modifier.statusBarsPadding() else Modifier)
        .padding(horizontal = 16.dp, vertical = 12.dp)
        .clip(RoundedCornerShape(24.dp))
        .background(Color(0xFFF0F0F0))

    TextField(
        value = "",
        onValueChange = {},
        placeholder = { Text(content) },
        trailingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        modifier = modifier,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true
    )
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchBarPreview() {
    VKUSocialNetworkingTheme {
        SearchBar("Content")
    }
}