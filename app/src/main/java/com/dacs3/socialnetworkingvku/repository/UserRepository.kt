package com.dacs3.socialnetworkingvku.repository

import com.dacs3.socialnetworkingvku.data.user.User
import com.dacs3.socialnetworkingvku.data.user.requests.UserUpdateRequest
import com.dacs3.socialnetworkingvku.roomdata.post.PostDao
import com.dacs3.socialnetworkingvku.security.TokenStoreManager
import com.dacs3.socialnetworkingvku.testApi.AuthApiService
import com.dacs3.socialnetworkingvku.testApi.UserApiService
import kotlinx.coroutines.flow.first

class UserRepository (
    private val userApiService: UserApiService,
    private val tokenStoreManager: TokenStoreManager
){
    suspend fun updateUser(request:UserUpdateRequest): Result<User>{
        val tokenDataStore = tokenStoreManager.accessTokenFlow.first()
        val token = "Bearer $tokenDataStore"
        return try{
            val response = userApiService.updateUser(token, request)

            if(response.isSuccessful){
                val user = response.body()
                if(user != null){
                    Result.success(user)
                }else{
                    Result.failure(Exception("Empty response body"))
                }
            }else{
                Result.failure(Exception("Failed to update user: ${response.code()}"))
            }
        }
        catch (e: Exception){
            Result.failure(e)
        }


    }

}