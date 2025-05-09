package com.dacs3.socialnetworkingvku.security

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.dacs3.socialnetworkingvku.data.user.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class TokenStoreManager(private val context: Context) {

    // Các khóa để lưu thông tin người dùng và token
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")

        // Các khóa thông tin người dùng
        private val USER_ID = longPreferencesKey("user_id")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_AVATAR = stringPreferencesKey("user_avatar")
    }

    // Lưu token vào DataStore
    suspend fun saveToken(accessToken: String, refreshToken: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = accessToken
            prefs[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    // Lưu thông tin người dùng vào DataStore
    suspend fun saveUser(user: User) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID] = user.id // Lưu ID người dùng
            prefs[USER_EMAIL] = user.email ?: "" // Lưu email của người dùng
            prefs[USER_NAME] = user.name ?: "" // Lưu tên của người dùng
            prefs[USER_AVATAR] = user.avatar ?: "" // Lưu avatar của người dùng
        }
    }

    // Lấy thông tin người dùng từ DataStore
    val userFlow: Flow<User> = context.dataStore.data
        .map { prefs ->
            val userId = prefs[USER_ID]?:0
            val userEmail = prefs[USER_EMAIL] ?: ""
            val userName = prefs[USER_NAME] ?: ""
            val userAvatar = prefs[USER_AVATAR] ?: ""
            User(userId, userEmail, userName, userAvatar) // Trả về đối tượng User
        }

    // Lấy token từ DataStore
    val accessTokenFlow: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[TOKEN_KEY] }

    val refreshTokenFlow: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[REFRESH_TOKEN_KEY] }

    // Lấy thông tin người dùng từ DataStore
    val userIdFlow: Flow<Long?> = context.dataStore.data
        .map { prefs -> prefs[USER_ID] }

    val userEmailFlow: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[USER_EMAIL] }

    val userNameFlow: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[USER_NAME] }

    val userAvatarFlow: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[USER_AVATAR] }

    // Xóa token khỏi DataStore
    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
            prefs.remove(REFRESH_TOKEN_KEY)
        }
    }

    // Xóa thông tin người dùng khỏi DataStore
    suspend fun clearUser() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_ID)
            prefs.remove(USER_EMAIL)
            prefs.remove(USER_NAME)
            prefs.remove(USER_AVATAR)
        }
    }

    // Xóa tất cả thông tin (token và người dùng)
    suspend fun clearAll() {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
            prefs.remove(REFRESH_TOKEN_KEY)
            prefs.remove(USER_ID)
            prefs.remove(USER_EMAIL)
            prefs.remove(USER_NAME)
            prefs.remove(USER_AVATAR)
        }
    }
}

