package com.dacs3.socialnetworkingvku.testApi

import com.dacs3.socialnetworkingvku.data.auth.response.ApiResponse
import com.dacs3.socialnetworkingvku.data.post.requests.PostRequest
import com.dacs3.socialnetworkingvku.data.post.response.CommentResponse
import com.dacs3.socialnetworkingvku.data.post.response.Post
import com.dacs3.socialnetworkingvku.data.post.response.PostWithStatsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PostApiService {

    @GET("post/all")
    suspend fun getAllPostsForHome(): Response<List<PostWithStatsResponse>>

    @POST("post/create")
    suspend fun createPost(
        @Header("Authorization") authorization: String,
        @Body postRequest: PostRequest
    ): Response<Post>


    @POST("post/like/{id}")
    suspend fun likePost(
        @Header("Authorization") authorization: String,
        @Path("id") id: Long
    ): Response<ApiResponse>

    @GET("post/{postId}/comments")
    suspend fun getCommentsForPost(@Path("postId") postId: Long): Response<List<CommentResponse>>

    @FormUrlEncoded
    @POST("post/comment/create/{id}")
    suspend fun commentPost(
        @Header("Authorization") authorization: String,
        @Path("id") id: Long,
        @Field("content") content: String
    ): Response<ApiResponse>
}