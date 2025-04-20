package com.dacs3.socialnetworkingvku.data

data class Post(
    val username: String,
    val date: Long,
    val imgAvatar: String,
    val content: String,
    val imgContent: String?,  // cho mặc định để tránh lỗi null
    val likeCount: Int,
    val commentCount: Int,
    val shareCount: Int,
    val id: String
)
