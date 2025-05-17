package com.dacs3.socialnetworkingvku.testApi

import GeminiService
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
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    // Để bảo mật hơn, bạn nên lưu trữ API key trong strings.xml hoặc các phương thức bảo mật
    private const val API_KEY = "AIzaSyATjFGyfwQhGxxngmrxW0kOVmADAOlmEJE"

    // Cấu hình Retrofit với OkHttp Client và Interceptor
    val geminiService: GeminiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .url(chain.request().url.newBuilder().addQueryParameter("key", API_KEY).build()) // Thêm API key vào URL
                    .build()
                chain.proceed(request)
            }.build())
            .build()
            .create(GeminiService::class.java)
    }

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
