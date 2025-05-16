package com.dacs3.socialnetworkingvku.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(content: String, modifier: Modifier = Modifier, hasStatusBarPadding: Boolean = false) {
    val gradientBorder = Brush.horizontalGradient(
        colors = listOf(Color(0xFF8F1EF5), Color(0xFF182481)) // xanh lá nhạt đến đậm
    )

    val finalModifier = modifier
        .then(if (hasStatusBarPadding) Modifier.statusBarsPadding() else Modifier)
        .background(Color.White, RoundedCornerShape(24.dp))
        .border(
            width = 2.dp,
            brush = gradientBorder,
            shape = RoundedCornerShape(24.dp)
        )
        .padding(horizontal = 12.dp)

    TextField(
        value = "",
        onValueChange = {},
        placeholder = { Text(content, color = Color.Gray) },
        trailingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        modifier = finalModifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true
    )
}
