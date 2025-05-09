package com.dacs3.socialnetworkingvku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cloudinary.android.MediaManager
import com.dacs3.socialnetworkingvku.navigation.AppNavigation
import com.dacs3.socialnetworkingvku.repository.AuthRepository
import com.dacs3.socialnetworkingvku.repository.PostRepository
import com.dacs3.socialnetworkingvku.roomdata.AppDatabase
import com.dacs3.socialnetworkingvku.security.TokenStoreManager
import com.dacs3.socialnetworkingvku.testApi.ApiService
import com.dacs3.socialnetworkingvku.testApi.RetrofitClient
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme
import com.dacs3.socialnetworkingvku.viewmodel.AuthViewModel
import com.dacs3.socialnetworkingvku.viewmodel.PostViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val tokenStore = TokenStoreManager(applicationContext)

        val retrofit = RetrofitClient.provideRetrofit(applicationContext, tokenStore)
        val apiService = retrofit.create(ApiService::class.java)

        val authRepository = AuthRepository(apiService, tokenStore)

        val postDao = AppDatabase.getInstance(applicationContext).postDao()
        val postRepository = PostRepository(apiService, postDao, tokenStore)

        val authViewModel = AuthViewModel(authRepository, tokenStore)
        val postViewModel = PostViewModel(postRepository)
        val context = this
        val config = mapOf(
            "cloud_name" to "de19voxxj",
            "api_key" to "313163933254635",
            "api_secret" to "wyU6rm-15kXjqEn4ElwG6zwhLaE"
        )
        MediaManager.init(this, config)
        setContent {
            VKUSocialNetworkingTheme {
                AppNavigation(viewModel = authViewModel, postViewModel = postViewModel, context = context)
            }
        }
    }
}
