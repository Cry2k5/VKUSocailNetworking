package com.dacs3.socialnetworkingvku.navigation

import MenuScreen
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dacs3.socialnetworkingvku.ui.screen.chat.ChatScreen
import com.dacs3.socialnetworkingvku.ui.screen.chat.MessageScreen
import com.dacs3.socialnetworkingvku.ui.screen.followers.FollowersScreen
import com.dacs3.socialnetworkingvku.ui.screen.home.CreatePostScreen
import com.dacs3.socialnetworkingvku.ui.screen.home.HomeScreen
import com.dacs3.socialnetworkingvku.ui.screen.home.PostCommentScreen
import com.dacs3.socialnetworkingvku.ui.screen.login_signup.AccountVerificationScreen
import com.dacs3.socialnetworkingvku.ui.screen.login_signup.ChangePasswordScreen
import com.dacs3.socialnetworkingvku.ui.screen.login_signup.ForgotPasswordScreen
import com.dacs3.socialnetworkingvku.ui.screen.login_signup.LoginScreen
import com.dacs3.socialnetworkingvku.ui.screen.login_signup.RegisterScreen
import com.dacs3.socialnetworkingvku.ui.screen.notification.NotificationScreen
import com.dacs3.socialnetworkingvku.ui.screen.profile.ProfileScreen
import com.dacs3.socialnetworkingvku.viewmodel.AuthViewModel
import com.dacs3.socialnetworkingvku.viewmodel.ChatViewModel
import com.dacs3.socialnetworkingvku.viewmodel.FollowerViewModel
import com.dacs3.socialnetworkingvku.viewmodel.GeminiViewModel
import com.dacs3.socialnetworkingvku.viewmodel.PostViewModel
import com.dacs3.socialnetworkingvku.viewmodel.UserViewModel

@Composable
fun AppNavigation(viewModel: AuthViewModel, postViewModel: PostViewModel, userViewModel: UserViewModel,context:Context, followerViewModel: FollowerViewModel, chatViewModel: ChatViewModel,
                  geminiViewModel: GeminiViewModel) {
    val navController = rememberNavController()

    val isLoggedIn by viewModel.isLoggedIn.collectAsState()


    // Khi trạng thái isLoggedIn thay đổi sẽ điều hướng
    LaunchedEffect(isLoggedIn) {
        Log.d("AppNavigation", "isLoggedIn: $isLoggedIn")
        if (isLoggedIn) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }  // loại bỏ login khỏi back stack
                launchSingleTop = true
            }
        } else {
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
                launchSingleTop = true
            }
        }
    }
    NavHost(navController = navController, startDestination = if (isLoggedIn) "home" else "login") {
        composable("login") {
            LoginScreen(viewModel = viewModel, navController = navController)
        }
        composable("register") { RegisterScreen(navController = navController, viewModel = viewModel) }

        composable("verify_otp") { AccountVerificationScreen(navController = navController, viewModel = viewModel) }

        composable("forgot_password") { ForgotPasswordScreen(viewModel = viewModel, navController = navController) }

        composable("home") { HomeScreen(viewModel = viewModel, controller = navController, postViewModel = postViewModel)}

        composable("create_post"){
            CreatePostScreen( postViewModel = postViewModel, navController = navController, geminiViewModel = geminiViewModel,context = context)
        }
        composable("followers") { FollowersScreen(navController = navController, followerViewModel = followerViewModel) }
        composable("chat") { MessageScreen(navController = navController, followerViewModel = followerViewModel) }
        composable("notification") { NotificationScreen(navController = navController) }
        composable("profile") { ProfileScreen(navController = navController, userViewModel =  userViewModel) }
        composable("change_password") {
            ChangePasswordScreen(navController = navController, authViewModel = viewModel)
        }
        composable("menu"){
            MenuScreen(navController = navController, authViewModel = viewModel, userViewModel = userViewModel)
        }

        composable("comments/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")?.toLongOrNull()
            postId?.let {
                PostCommentScreen(postId = it, navController = navController, postViewModel = postViewModel)
            }
        }


        // NavGraph
        composable(
            route = "chat/{currentUserId}/{receiverId}/{receiverName}",
            arguments = listOf(
                navArgument("currentUserId") { type = NavType.LongType },
                navArgument("receiverId") { type = NavType.LongType },
                navArgument("receiverName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val currentUserId = backStackEntry.arguments?.getLong("currentUserId") ?: 0L
            val receiverId = backStackEntry.arguments?.getLong("receiverId") ?: 0L
            val receiverName = backStackEntry.arguments?.getString("receiverName") ?: ""

            ChatScreen(
                currentUserId = currentUserId,
                receiverId = receiverId,
                receiverName = receiverName,
                chatViewModel = chatViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

    }
}
