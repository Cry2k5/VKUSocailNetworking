package com.dacs3.socialnetworkingvku.data.auth.response

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: Any?
)