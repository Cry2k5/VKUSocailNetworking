package com.dacs3.socialnetworkingvku.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("token")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    )