package com.dacs3.socialnetworkingvku.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.dacs3.socialnetworkingvku.data.user.UserDto
import com.dacs3.socialnetworkingvku.data.user.requests.UserUpdateRequest
import com.dacs3.socialnetworkingvku.roomdata.post.PostDao
import com.dacs3.socialnetworkingvku.security.TokenStoreManager
import com.dacs3.socialnetworkingvku.testApi.AuthApiService
import com.dacs3.socialnetworkingvku.testApi.UserApiService
import kotlinx.coroutines.flow.first
import java.io.ByteArrayOutputStream

class UserRepository (
    private val userApiService: UserApiService,
    private val tokenStoreManager: TokenStoreManager
) {
    suspend fun getUserInfo(): Result<UserDto> {
        val tokenDataStore = tokenStoreManager.accessTokenFlow.first()
        val token = "Bearer $tokenDataStore"

        return try {
            val response = userApiService.getUserInfo(token)
            Log.d("UserRepository", "Response: $response")
            if (response.isSuccessful) {
                val user = response.body()
                if (user != null) {
                    tokenStoreManager.saveUserDto(user)
                    val savedUser = tokenStoreManager.userDtoFlow.first()
                    Log.d("UserRepository", "UserInDataStore: $savedUser")
                    Log.d("UserRepository", "User: $user")
                    Result.success(user)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(Exception("Failed to fetch user info: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUser(request: UserUpdateRequest): Result<UserDto> {
        val tokenDataStore = tokenStoreManager.accessTokenFlow.first()
        val token = "Bearer $tokenDataStore"
        return try {
            val response = userApiService.updateUser(token, request)
            Log.d("UserRepository", "Response: $response")
            if (response.isSuccessful) {
                val user = response.body()
                if (user != null) {
                    Result.success(user)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(Exception("Failed to update user: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }


    }

    suspend fun deleteUser(): Result<Unit> {
        val tokenDataStore = tokenStoreManager.accessTokenFlow.first()
        val token = "Bearer $tokenDataStore"
        return try {
            val response = userApiService.deleteUser(token)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to delete user: ${response.code()}"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun uploadAvatarToCloudinary(imageUri: Uri, context: Context): String? =
        kotlinx.coroutines.suspendCancellableCoroutine { continuation ->
            try {
                // Chuyển Uri thành ByteArray
                val imageBytes = uriToByteArray(context, imageUri)
                if (imageBytes == null) {
                    continuation.resume(null, null)
                    return@suspendCancellableCoroutine
                }

                // Upload ảnh lên Cloudinary với các tham số resize và folder
                MediaManager.get().upload(imageBytes)
                    .option("resource_type", "image")
                    .option("upload_preset", "ml_default")
                    .option("folder", "avatars")  // Thêm tham số để upload vào folder 'posts'
                    .callback(object : UploadCallback {
                        override fun onStart(requestId: String?) {}

                        override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {}

                        override fun onSuccess(requestId: String?, resultData: Map<*, *>?) {
                            // Lấy URL của ảnh đã upload
                            val originalUrl = resultData?.get("secure_url") as? String

                            // Chỉnh sửa URL để trả về ảnh với độ phân giải thấp (800x600)
                            val resizedUrl = originalUrl?.replace("/upload/", "/upload/w_800,h_600,c_limit/")

                            // Trả về URL ảnh đã chỉnh sửa
                            continuation.resume(resizedUrl, null)
                        }

                        override fun onError(requestId: String?, error: ErrorInfo?) {
                            Log.e("CloudinaryUpload", "Error uploading image: ${error?.description}")
                            continuation.resume(null, null)
                        }

                        override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                            Log.w("CloudinaryUpload", "Upload rescheduled: ${error?.description}")
                            continuation.resume(null, null)
                        }
                    })
                    .dispatch()
            } catch (e: Exception) {
                e.printStackTrace()
                continuation.resume(null, null)
            }
        }


    fun uriToByteArray(context: Context, uri: Uri): ByteArray? {
        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val byteArrayOutputStream = ByteArrayOutputStream()
                inputStream.copyTo(byteArrayOutputStream)
                byteArrayOutputStream.toByteArray()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}