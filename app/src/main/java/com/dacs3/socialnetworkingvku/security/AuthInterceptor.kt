package com.dacs3.socialnetworkingvku.security

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

// AuthInterceptor chỉ thêm token vào header
class AuthInterceptor(private val tokenManager: TokenStoreManager?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenManager?.accessTokenFlow?.first()
        }

        val requestBuilder = chain.request().newBuilder()
        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}
