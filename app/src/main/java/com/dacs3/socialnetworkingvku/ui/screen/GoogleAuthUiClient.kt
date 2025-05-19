package com.dacs3.socialnetworkingvku.ui.screen

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import kotlinx.coroutines.tasks.await

class GoogleAuthUiClient(private val context: Context) {
    private val oneTapClient = com.google.android.gms.auth.api.identity.Identity.getSignInClient(context)
    private val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId("465670121134-tjrhiuu8h9pa848qpi1uvi71tpj4j0qk.apps.googleusercontent.com")
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()

    suspend fun signIn(): IntentSender? {
        return try {
            val result = oneTapClient.beginSignIn(signInRequest).await()
            result.pendingIntent.intentSender
        } catch (e: Exception) {
            e.printStackTrace()
            null // Hoặc xử lý lỗi tùy bạn
        }
    }

    fun getGoogleIdTokenFromIntent(intent: Intent): String? {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        return credential.googleIdToken
    }
}