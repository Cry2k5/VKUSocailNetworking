package com.dacs3.socialnetworkingvku.data.post.response

import com.dacs3.socialnetworkingvku.data.post.requests.PostGetRequest
import com.dacs3.socialnetworkingvku.data.user.User
import com.google.gson.annotations.SerializedName

data class PostWithStatsResponse(
    @SerializedName("post")
    val post: PostGetRequest,

    @SerializedName("likeCount")
    var likeCount: Int,

    @SerializedName("commentCount")
    val commentCount: Int,

    @SerializedName("isLiked")
    var isLiked:Boolean
)