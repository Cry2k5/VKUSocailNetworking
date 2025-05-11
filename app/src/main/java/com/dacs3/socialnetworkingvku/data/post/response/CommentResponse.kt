package com.dacs3.socialnetworkingvku.data.post.response

import com.dacs3.socialnetworkingvku.data.user.User
import com.dacs3.socialnetworkingvku.data.user.UserComment

data class CommentResponse(
    val commentId: Long,
    val user: UserComment,
    val content: String,
    val createdAt: String,

)