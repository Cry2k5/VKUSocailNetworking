package com.dacs3.socialnetworkingvku.navigation

import CreatePostScreen
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dacs3.socialnetworkingvku.ui.screen.chat.ChatScreen
import com.dacs3.socialnetworkingvku.ui.screen.chat.MessageScreen
import com.dacs3.socialnetworkingvku.ui.screen.followers.FollowersScreen
import com.dacs3.socialnetworkingvku.ui.screen.home.HomeScreen
import com.dacs3.socialnetworkingvku.ui.screen.home.PostCommentScreen
import com.dacs3.socialnetworkingvku.ui.screen.login_signup.AccountVerificationScreen
import com.dacs3.socialnetworkingvku.ui.screen.login_signup.ForgotPasswordScreen
import com.dacs3.socialnetworkingvku.ui.screen.login_signup.LoginScreen
import com.dacs3.socialnetworkingvku.ui.screen.login_signup.RegisterScreen
import com.dacs3.socialnetworkingvku.ui.screen.notification.NotificationScreen
import com.dacs3.socialnetworkingvku.ui.screen.profile.ProfileScreen
import com.dacs3.socialnetworkingvku.viewmodel.AuthViewModel
import com.dacs3.socialnetworkingvku.viewmodel.PostViewModel
import com.dacs3.socialnetworkingvku.viewmodel.UserViewModel

@Composable
fun AppNavigation(viewModel: AuthViewModel, postViewModel: PostViewModel, userViewModel: UserViewModel,context:Context) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(viewModel = viewModel, navController = navController)
        }
        composable("register") { RegisterScreen(navController = navController, viewModel = viewModel) }

        composable("verify_otp") { AccountVerificationScreen(navController = navController, viewModel = viewModel) }

        composable("forgot_password") { ForgotPasswordScreen(viewModel = viewModel, navController = navController) }

        composable("home") { HomeScreen(viewModel = viewModel, controller = navController, postViewModel = postViewModel)}

        composable("create_post"){
            CreatePostScreen(controller = navController, postViewModel = postViewModel, context = context)
        }
        composable("followers") { FollowersScreen(navController = navController) }
        composable("chat") { MessageScreen(navController = navController) }
        composable("notification") { NotificationScreen(navController = navController) }
        composable("profile") { ProfileScreen(navController = navController, userViewModel =  userViewModel) }


        composable("comments/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")?.toLongOrNull()
            postId?.let {
                PostCommentScreen(postId = it, navController = navController, postViewModel = postViewModel)
            }
        }


    }
}
