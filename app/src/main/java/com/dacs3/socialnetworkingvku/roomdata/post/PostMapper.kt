package com.dacs3.socialnetworkingvku.roomdata.post

import com.dacs3.socialnetworkingvku.data.post.response.PostWithStatsResponse

fun PostWithStatsResponse.toEntity(): PostEntity {
    return PostEntity(
        postId = post.post_id,
        userId = post.userdto.id,
        userName = post.userdto.name,
        userEmail = post.userdto.email,
        userAvatar = post.userdto.avatar.takeIf { it.isNotBlank() }, // Chỉ lưu nếu không rỗng
        content = post.content,
        image = post.image,
        video = post.video,
        createdAt = post.create_at,
        likeCount = likeCount,
        commentCount = commentCount,
        isLiked = isLiked
    )
}
