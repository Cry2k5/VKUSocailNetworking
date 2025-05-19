package com.dacs3.socialnetworkingvku.testApi

import com.dacs3.socialnetworkingvku.data.auth.requests.RegisterRequest
import com.dacs3.socialnetworkingvku.data.auth.response.LoginResponse
import com.dacs3.socialnetworkingvku.data.auth.response.ApiResponse
import com.dacs3.socialnetworkingvku.data.post.requests.PostRequest
import com.dacs3.socialnetworkingvku.data.post.response.Post
import com.dacs3.socialnetworkingvku.data.post.response.PostWithStatsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {

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

    @POST("auth/forgotPassword")
    suspend fun forgotPassword(
        @Query("email") email: String
    ): Response<ApiResponse>

    @PATCH("auth/verifyOtpPassword")
    suspend fun verifyOtpPassword(
        @Query("email") email: String,
        @Query("otp") otp: String
    ): Response<Any>

    @FormUrlEncoded
    @POST("auth/logout")
    suspend fun logout(
        @Field("email") email: String
    ): Response<ApiResponse>

    @POST("auth/google")
    @FormUrlEncoded
    suspend fun loginWithGoogle(@Field("token") idToken: String): Response<LoginResponse>

}
