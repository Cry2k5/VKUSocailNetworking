package com.dacs3.socialnetworkingvku.testApi

import com.dacs3.socialnetworkingvku.data.post.requests.PostRequest
import com.dacs3.socialnetworkingvku.data.post.response.Post
import com.dacs3.socialnetworkingvku.data.user.User
import com.dacs3.socialnetworkingvku.data.user.requests.UserUpdateRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApiService {
    @POST("user/update")
    suspend fun updateUser(
        @Header("Authorization") authorization: String,
        @Body request: UserUpdateRequest
    ): Response<User>

}