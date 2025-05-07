package com.dacs3.socialnetworkingvku.security

import okhttp3.Interceptor
import okhttp3.Response

// AuthInterceptor chỉ thêm token vào header
class AuthInterceptor(private val token: String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            token?.let {
                header("Authorization", "Bearer $it")  // Thêm token vào header
            }
        }.build()

        return chain.proceed(request)
    }
}
