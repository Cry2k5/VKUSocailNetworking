package com.dacs3.socialnetworkingvku.testApi

import com.dacs3.socialnetworkingvku.data.post.requests.PostRequest
import com.dacs3.socialnetworkingvku.data.post.response.Post
import com.dacs3.socialnetworkingvku.data.post.response.PostWithStatsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface PostApiService {
    @GET("post/all")
    suspend fun getAllPostsForHome(): Response<List<PostWithStatsResponse>>

    @POST("post/create")
    suspend fun createPost(
        @Header("Authorization") authorization: String,
        @Body postRequest: PostRequest
    ): Response<Post>
}