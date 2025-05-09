package com.dacs3.socialnetworkingvku.data.post.requests

data class PostRequest (
    val content: String,
    val imageUrl: String? = null,
    val videoUrl: String? = null,
)