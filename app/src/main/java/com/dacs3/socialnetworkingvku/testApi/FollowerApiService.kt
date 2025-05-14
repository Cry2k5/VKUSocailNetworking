package com.dacs3.socialnetworkingvku.testApi

import com.dacs3.socialnetworkingvku.data.auth.response.ApiResponse
import com.dacs3.socialnetworkingvku.data.post.response.CommentResponse
import com.dacs3.socialnetworkingvku.data.user.UserDto
import com.dacs3.socialnetworkingvku.data.user.UserFollowingDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FollowerApiService {

    @GET("follower/GetFollowing")
    suspend fun getFollowing(@Header("Authorization") authorization: String)
    : Response<List<UserFollowingDto>>

    @GET("follower/GetPeople")
    suspend fun getPeple(@Header("Authorization") authorization: String)
    : Response<List<UserFollowingDto>>

    @POST("follower/Follow")
    suspend fun follow(@Header("Authorization") authorization: String, @Body userId: Long): Response<ApiResponse>

    @DELETE("follower/unfollow")
    suspend fun unfollow(@Header("Authorization") authorization: String, @Query("userId") userId: Long): Response<ApiResponse>





}