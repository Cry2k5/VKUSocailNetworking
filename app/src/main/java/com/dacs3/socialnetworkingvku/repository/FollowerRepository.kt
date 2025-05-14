package com.dacs3.socialnetworkingvku.repository

import android.util.Log
import com.dacs3.socialnetworkingvku.data.auth.response.ApiResponse
import com.dacs3.socialnetworkingvku.data.user.UserFollowingDto
import com.dacs3.socialnetworkingvku.security.TokenStoreManager
import com.dacs3.socialnetworkingvku.testApi.FollowerApiService
import kotlinx.coroutines.flow.first

class FollowerRepository(
    private val tokenStoreManager: TokenStoreManager,
    private val followingApiService: FollowerApiService
) {


    suspend fun getFollowing(): Result<List<UserFollowingDto>> {
        return try {
            val token = "Bearer ${tokenStoreManager.accessTokenFlow.first()}"
            val response = followingApiService.getFollowing(token)

            if (response.isSuccessful) {
                val followingList = response.body()
                if (followingList != null) {
                    Result.success(followingList)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(Exception("Failed to fetch following: ${response.code()}"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    suspend fun getPeople(): Result<List<UserFollowingDto>> {
        return try {
            val token = "Bearer ${tokenStoreManager.accessTokenFlow.first()}"
            val response = followingApiService.getPeple(token)
            if (response.isSuccessful) {
                val peopleList = response.body()
                if (peopleList != null) {
                    Result.success(peopleList)
                } else {
                    Result.failure(Exception("Empty response body"))
                }

            } else {
                Result.failure(Exception("Failed to fetch people: ${response.code()}"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun follow(userId: Long): Result<ApiResponse> {

        return try {
            val token = "Bearer ${tokenStoreManager.accessTokenFlow.first()}"
            val response = followingApiService.follow(token, userId)

            if (response.isSuccessful) {
                Log.d("FollowerRepository", "Follow successful")
                val followResponse = response.body()
                if (followResponse != null) {
                    Result.success(followResponse)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(Exception("Failed to follow: ${response.code()}"))
            }
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun unfollow(userId: Long): Result<ApiResponse> {
        return try {
            val token = "Bearer ${tokenStoreManager.accessTokenFlow.first()}"
            val response = followingApiService.unfollow(token, userId)
            Log.d("FollowerRepository", "Unfollow successful")
            if (response.isSuccessful) {
                val unfollowResponse = response.body()
                if (unfollowResponse != null) {
                    Result.success(unfollowResponse)
                } else {
                    Result.failure(Exception("Empty response body"))
            }
                } else {
                Result.failure(Exception("Failed to unfollow: ${response.code()}"))
            }
    }
        catch (e: Exception) {
            Result.failure(e)
        }
    }
}


