package com.dacs3.socialnetworkingvku.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dacs3.socialnetworkingvku.data.User
import com.dacs3.socialnetworkingvku.ui.screen.home.HomeScreen
import com.dacs3.socialnetworkingvku.ui.screen.login_signup.AccountVerificationScreen
import com.dacs3.socialnetworkingvku.ui.screen.login_signup.LoginScreen
import com.dacs3.socialnetworkingvku.ui.screen.login_signup.RegisterScreen
import com.dacs3.socialnetworkingvku.viewmodel.AuthViewModel

@Composable
fun AppNavigation(viewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(viewModel = viewModel, navController = navController)
        }
        composable("register") { RegisterScreen(navController = navController, viewModel = viewModel) }

        composable("verify_otp") { AccountVerificationScreen(navController = navController, viewModel = viewModel) }

        composable("home") { HomeScreen(viewModel = viewModel, controller = navController)}

//        composable("create_post"){
//            CreatePostScreen()
//        }

    }
}
