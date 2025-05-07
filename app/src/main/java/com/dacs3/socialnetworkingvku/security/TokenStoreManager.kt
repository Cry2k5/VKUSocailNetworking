package com.dacs3.socialnetworkingvku.security

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class TokenStoreManager(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }

    suspend fun saveToken(accessToken: String, refreshToken: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = accessToken
            prefs[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    val accessTokenFlow: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[TOKEN_KEY] }

    val refreshTokenFlow: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[REFRESH_TOKEN_KEY] }
}
