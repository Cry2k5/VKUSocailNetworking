package com.dacs3.socialnetworkingvku.data.post.response

import com.dacs3.socialnetworkingvku.data.post.requests.PostGetRequest
import com.google.gson.annotations.SerializedName

data class PostWithStatsResponse(
    @SerializedName("post")
    val post: PostGetRequest,
    val likeCount: Int,
    val commentCount: Int
)