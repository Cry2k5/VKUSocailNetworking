package com.dacs3.socialnetworkingvku.data.post.response

import com.dacs3.socialnetworkingvku.data.group.Group
import com.dacs3.socialnetworkingvku.data.user.User
import java.time.LocalDateTime

data class Post(
    val postId: Long = 0,

    var user: User,

    val group: Group? = null,

    var content: String = "",

    var image: String? = null,

    var video: String? = null,

    val createAt: LocalDateTime = LocalDateTime.now()
)