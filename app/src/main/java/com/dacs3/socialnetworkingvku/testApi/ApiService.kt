package com.dacs3.socialnetworkingvku.testApi

import com.dacs3.socialnetworkingvku.data.auth.requests.RegisterRequest
import com.dacs3.socialnetworkingvku.data.auth.response.LoginResponse
import com.dacs3.socialnetworkingvku.data.auth.response.ApiResponse
import com.dacs3.socialnetworkingvku.data.post.requests.PostRequest
import com.dacs3.socialnetworkingvku.data.post.response.CloudinaryResponse
import com.dacs3.socialnetworkingvku.data.post.response.Post
import com.dacs3.socialnetworkingvku.data.post.response.PostWithStatsResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String

    ): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<ApiResponse>

    @FormUrlEncoded
    @POST("auth/verifyOtp")
    suspend fun verifyOtp(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("address") address: String,
        @Field("otp") otp: String,
        @Field("password") password: String,
        @Field("dateOfBirth") dateOfBirth: String,
        @Field("phone") phone: String,
        @Field("school") school: String
    ): Response<ApiResponse>

    @FormUrlEncoded
    @POST("auth/logout")
    suspend fun logout(
        @Field("email") email: String
    ): Response<ApiResponse>


    @GET("post/all")
    suspend fun getAllPostsForHome(): Response<List<PostWithStatsResponse>>

    @POST("post/create")
    suspend fun createPost(
        @Header("Authorization") authorization: String,
        @Body postRequest: PostRequest
    ): Response<Post>

    @Multipart
    @POST("https://api.cloudinary.com/v1_1/SocialNetwork/image/upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("upload_preset") uploadPreset: String
    ): Response<CloudinaryResponse>
//    wyU6rm-15kXjqEn4ElwG6zwhLaE: secrey key
//    313163933254635: api key
}
