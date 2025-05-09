package com.dacs3.socialnetworkingvku.data.post.requests

import com.dacs3.socialnetworkingvku.data.user.User
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class PostGetRequest(
    val post_id: Long,
    val userdto: User,
    val content: String,
    val image: String ?= null,
    val video: String ?= null,
    val create_at: String,
)