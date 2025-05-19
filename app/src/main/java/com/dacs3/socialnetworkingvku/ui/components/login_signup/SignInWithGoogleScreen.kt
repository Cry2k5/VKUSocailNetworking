package com.dacs3.socialnetworkingvku.ui.components.login_signup

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dacs3.socialnetworkingvku.R
import com.dacs3.socialnetworkingvku.ui.screen.GoogleAuthUiClient
import com.dacs3.socialnetworkingvku.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun SignInWithGoogleScreen(
    viewModel: AuthViewModel,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val googleClient = GoogleAuthUiClient(context)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            coroutineScope.launch {
                try {
                    val idToken = result.data?.let { googleClient.getGoogleIdTokenFromIntent(it) }
                    Log.d("GoogleSignIn", "ID Token: $idToken")
                    idToken?.let {
                        viewModel.loginWithGoogle(it)
                    } ?: run {
                        Toast.makeText(context, "Không lấy được ID token", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("GoogleSignIn", "Lỗi khi lấy ID token", e)
                    Toast.makeText(context, "Lấy ID token thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    OutlinedButton(
        onClick = {
            coroutineScope.launch {
                try {
                    val intentSender = googleClient.signIn()
                    intentSender?.let {
                        launcher.launch(IntentSenderRequest.Builder(it).build())
                    } ?: run {
                        Toast.makeText(context, "Không thể lấy được intent", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("GoogleSignIn", "Sign-in failed", e)
                    Toast.makeText(context, "Đăng nhập Google lỗi: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = "Google Sign In",
            modifier = Modifier.size(24.dp) ,
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Đăng nhập bằng Google")
    }
}
