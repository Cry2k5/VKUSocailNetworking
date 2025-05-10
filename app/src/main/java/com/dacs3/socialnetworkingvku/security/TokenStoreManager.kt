package com.dacs3.socialnetworkingvku.security

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.dacs3.socialnetworkingvku.data.user.User
import com.dacs3.socialnetworkingvku.data.user.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// DataStore extensions
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")
val Context.dataStoreUpdateUser: DataStore<Preferences> by preferencesDataStore(name = "userDto_prefs")

class TokenStoreManager(private val context: Context) {

    companion object {
        // Token keys
        private val TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")

        // Basic User keys
        private val USER_ID = longPreferencesKey("user_id")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_AVATAR = stringPreferencesKey("user_avatar")

        // Extended UserDto keys
        private val USER_ADDRESS = stringPreferencesKey("user_address")
        private val USER_DATE_OF_BIRTH = stringPreferencesKey("user_date_of_birth")
        private val USER_PHONE = stringPreferencesKey("user_phone")
        private val USER_SCHOOL = stringPreferencesKey("user_school")
        private val USER_BIO = stringPreferencesKey("user_bio")
    }

    /** Save access and refresh token */
    suspend fun saveToken(accessToken: String, refreshToken: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = accessToken
            prefs[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    /** Save basic user info */
    suspend fun saveUser(user: User) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID] = user.id
            prefs[USER_EMAIL] = user.email ?: ""
            prefs[USER_NAME] = user.name ?: ""
            prefs[USER_AVATAR] = user.avatar ?: ""
        }
    }

    /** Save full user profile */
    suspend fun saveUserDto(user: UserDto) {
        context.dataStoreUpdateUser.edit { prefs ->
            prefs[USER_ID] = user.userId
            prefs[USER_EMAIL] = user.email ?: ""
            prefs[USER_NAME] = user.username ?: ""
            prefs[USER_AVATAR] = user.avatar ?: ""
            prefs[USER_ADDRESS] = user.address ?: ""
            prefs[USER_DATE_OF_BIRTH] = user.dateOfBirth ?: ""
            prefs[USER_PHONE] = user.phoneNumber ?: ""
            prefs[USER_SCHOOL] = user.school ?: ""
            prefs[USER_BIO] = user.bio ?: ""
        }
    }

    /** Observe basic user data */
    val userFlow: Flow<User> = context.dataStore.data.map { prefs ->
        User(
            id = prefs[USER_ID] ?: 0,
            email = prefs[USER_EMAIL] ?: "",
            name = prefs[USER_NAME] ?: "",
            avatar = prefs[USER_AVATAR] ?: ""
        )
    }

    /** Observe full user profile */
    val userDtoFlow: Flow<UserDto> = context.dataStoreUpdateUser.data.map { prefs ->
        UserDto(
            userId = prefs[USER_ID] ?: 0,
            email = prefs[USER_EMAIL] ?: "",
            username = prefs[USER_NAME] ?: "",
            address = prefs[USER_ADDRESS] ?: "",
            dateOfBirth = prefs[USER_DATE_OF_BIRTH] ?: "",
            bio = prefs[USER_BIO] ?: "",
            school = prefs[USER_SCHOOL] ?: "",
            avatar = prefs[USER_AVATAR] ?: "",
            phoneNumber = prefs[USER_PHONE] ?: ""
        )
    }

    /** Observe individual fields */
    val accessTokenFlow: Flow<String?> = context.dataStore.data.map { it[TOKEN_KEY] }
    val refreshTokenFlow: Flow<String?> = context.dataStore.data.map { it[REFRESH_TOKEN_KEY] }
    val userIdFlow: Flow<Long?> = context.dataStore.data.map { it[USER_ID] }
    val userEmailFlow: Flow<String?> = context.dataStore.data.map { it[USER_EMAIL] }
    val userNameFlow: Flow<String?> = context.dataStore.data.map { it[USER_NAME] }
    val userAvatarFlow: Flow<String?> = context.dataStore.data.map { it[USER_AVATAR] }

    /** Clear only tokens */
    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
            prefs.remove(REFRESH_TOKEN_KEY)
        }
    }

    /** Clear basic user data */
    suspend fun clearUser() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_ID)
            prefs.remove(USER_EMAIL)
            prefs.remove(USER_NAME)
            prefs.remove(USER_AVATAR)
        }
    }

    /** Clear all user and token data */
    suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
        context.dataStoreUpdateUser.edit { it.clear() }
    }
}
