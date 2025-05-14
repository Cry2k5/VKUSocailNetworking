package com.dacs3.socialnetworkingvku.roomdata.post

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val postId: Long,

    val userId: Long,
    val userName: String,
    val userEmail: String,
    val userAvatar: String?,

    val content: String,
    val image: String?,
    val video: String?,
    val createdAt: String,

    val likeCount: Int,
    val commentCount: Int,
    val isLiked: Boolean
)
