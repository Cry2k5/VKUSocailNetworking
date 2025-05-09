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

    val gson = GsonBuilder().setLenient().create()
    fun provideRetrofit(context: Context, tokenStoreManager: TokenStoreManager): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenStoreManager))
            .connectTimeout(60, TimeUnit.SECONDS) // Tăng timeout kết nối
            .writeTimeout(60, TimeUnit.SECONDS)   // Tăng timeout ghi
            .readTimeout(60, TimeUnit.SECONDS)    // Tăng timeout đọc
            .build()

        return Retrofit.Builder()
            .baseUrl("http://192.168.2.88:8080/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

}
