package com.dacs3.socialnetworkingvku.navigation

import CreatePostScreen
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dacs3.socialnetworkingvku.ui.screen.home.HomeScreen
import com.dacs3.socialnetworkingvku.ui.screen.login_signup.AccountVerificationScreen
import com.dacs3.socialnetworkingvku.ui.screen.login_signup.LoginScreen
import com.dacs3.socialnetworkingvku.ui.screen.login_signup.RegisterScreen
import com.dacs3.socialnetworkingvku.viewmodel.AuthViewModel
import com.dacs3.socialnetworkingvku.viewmodel.PostViewModel

@Composable
fun AppNavigation(viewModel: AuthViewModel, postViewModel: PostViewModel, context:Context) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(viewModel = viewModel, navController = navController)
        }
        composable("register") { RegisterScreen(navController = navController, viewModel = viewModel) }

        composable("verify_otp") { AccountVerificationScreen(navController = navController, viewModel = viewModel) }

        composable("home") { HomeScreen(viewModel = viewModel, controller = navController, postViewModel = postViewModel)}

        composable("create_post"){
            CreatePostScreen(controller = navController, viewModel = postViewModel, context = context)
        }

    }
}
