package com.dacs3.socialnetworkingvku.data.auth.response

import com.dacs3.socialnetworkingvku.data.user.User
import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("accessToken")
    val accessToken: String?=null,
    @SerializedName("refreshToken")
    val refreshToken: String?=null,

    @SerializedName("user")
    val user: User?=null
    )