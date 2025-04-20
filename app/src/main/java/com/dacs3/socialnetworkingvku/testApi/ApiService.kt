package com.dacs3.socialnetworkingvku.testApi

import com.dacs3.socialnetworkingvku.data.Post
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("post")
    suspend fun getPosts(): Response<List<Post>>
}
