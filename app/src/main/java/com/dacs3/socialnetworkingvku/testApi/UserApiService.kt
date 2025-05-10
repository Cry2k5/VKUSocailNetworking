package com.dacs3.socialnetworkingvku.testApi

import com.dacs3.socialnetworkingvku.data.post.requests.PostRequest
import com.dacs3.socialnetworkingvku.data.post.response.Post
import com.dacs3.socialnetworkingvku.data.user.UserDto
import com.dacs3.socialnetworkingvku.data.user.requests.UserUpdateRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApiService {

    @GET("user/info")
    suspend fun getUserInfo(@Header("Authorization") authorization: String): Response<UserDto>

    @PUT("user/update")
    suspend fun updateUser(
        @Header("Authorization") authorization: String,
        @Body request: UserUpdateRequest
    ): Response<UserDto>

    @POST("user/remove")
    suspend fun deleteUser(@Header("Authorization") authorization: String): Response<Unit>


}