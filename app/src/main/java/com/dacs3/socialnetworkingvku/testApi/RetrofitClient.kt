package com.dacs3.socialnetworkingvku.testApi

import android.content.Context
import com.dacs3.socialnetworkingvku.security.AuthInterceptor
import com.dacs3.socialnetworkingvku.security.TokenStoreManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.first
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.2.79:8080/api/")  // Đảm bảo URL đúng
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS) // Tăng timeout kết nối
        .writeTimeout(60, TimeUnit.SECONDS)   // Tăng timeout ghi
        .readTimeout(60, TimeUnit.SECONDS)    // Tăng timeout đọc
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
    val gson = GsonBuilder().setLenient().create()
    fun provideRetrofit(context: Context, dataStoreManager: TokenStoreManager): Retrofit {
        val accessToken = runBlocking { dataStoreManager.accessTokenFlow.first() }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(accessToken))
            .connectTimeout(60, TimeUnit.SECONDS) // Tăng timeout kết nối
            .writeTimeout(60, TimeUnit.SECONDS)   // Tăng timeout ghi
            .readTimeout(60, TimeUnit.SECONDS)    // Tăng timeout đọc
            .build()

        return Retrofit.Builder()
            .baseUrl("http://192.168.2.79:8080/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
}
