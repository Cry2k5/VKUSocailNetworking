package com.dacs3.socialnetworkingvku.testApi

import com.dacs3.socialnetworkingvku.data.requests.RegisterRequest
import com.dacs3.socialnetworkingvku.data.response.LoginResponse
import com.dacs3.socialnetworkingvku.data.response.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.time.LocalDate

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


    @GET("me")
    suspend fun getProfile(): Response<ApiResponse>

}
