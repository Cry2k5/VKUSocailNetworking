package com.dacs3.socialnetworkingvku

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dacs3.socialnetworkingvku.navigation.AppNavigation
import com.dacs3.socialnetworkingvku.repository.AuthRepository
import com.dacs3.socialnetworkingvku.security.TokenStoreManager
import com.dacs3.socialnetworkingvku.testApi.ApiService
import com.dacs3.socialnetworkingvku.testApi.RetrofitClient
import com.dacs3.socialnetworkingvku.ui.screen.login_signup.LoginScreen
import com.dacs3.socialnetworkingvku.ui.theme.VKUSocialNetworkingTheme
import com.dacs3.socialnetworkingvku.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val tokenStore = TokenStoreManager(applicationContext)
        val retrofit = RetrofitClient.provideRetrofit(applicationContext, tokenStore)
        val repository = AuthRepository(retrofit.create(ApiService::class.java), tokenStore)
        val viewModel = AuthViewModel(repository, tokenStore)

        setContent {
            VKUSocialNetworkingTheme {
                AppNavigation(viewModel = viewModel)
            }
        }
    }
}
