package com.dacs3.socialnetworkingvku.repository

import android.util.Log
import com.dacs3.socialnetworkingvku.data.User
import com.dacs3.socialnetworkingvku.data.requests.RegisterRequest
import com.dacs3.socialnetworkingvku.data.response.ApiResponse
import com.dacs3.socialnetworkingvku.security.TokenStoreManager
import com.dacs3.socialnetworkingvku.testApi.ApiService
import retrofit2.http.Field
import java.time.LocalDate

class AuthRepository(
    private val apiService: ApiService,
    private val tokenStoreManager: TokenStoreManager,
) {
    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            val response = apiService.login(email, password)
            Log.d("LoginScreen", "Raw response: $response")
            if (response.isSuccessful) {
                val body = response.body()
                Log.d("LoginScreen", "Response body: $body")

                if (body != null && body.user != null) {
                    val user = User(
                        id = body.user.id,
                        email = body.user.email,
                        name = body.user.name,
                        avatar = body.user.avatar
                    )
                    Log.d("LoginScreen", "User: $user")
                    tokenStoreManager.saveUser(user)
                    tokenStoreManager.saveToken(body.accessToken, body.refreshToken)
                    return Result.success(Unit)
                } else {
                    Log.e("LoginScreen", "Missing body or user")
                    return Result.failure(Exception("Missing user info"))
                }
            } else {
                Log.e("LoginScreen", "Login failed: ${response.code()}")
                return Result.failure(Exception("Login failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(registerRequest: RegisterRequest): Result<ApiResponse> {
        return try {
            // Log request data for debugging (optional)
            Log.d("AuthRepository", "Register request: $registerRequest")

            // Gửi request đến API
            val response = apiService.register(registerRequest)

            // Log mã trạng thái của response (successful or not)
            Log.d("AuthRepository", "API response status: ${response.isSuccessful}, code: ${response.code()}")

            // Nếu response thành công
            if (response.isSuccessful) {
                val apiResponse = response.body()

                // Log toàn bộ nội dung body của API response
                Log.d("AuthRepository", "API Response body: $apiResponse")

                if (apiResponse != null) {
                    // Kiểm tra thông báo từ server
                    if (apiResponse.message == "OTP sent to your email") {
                        // Log nếu OTP đã được gửi
                        Log.d("AuthRepository", "OTP sent to email successfully.")
                        Result.success(apiResponse)
                    } else {
                        // Log trường hợp API trả về thông báo khác
                        Log.d("AuthRepository", "Registration success, but with a different message: ${apiResponse.message}")
                        Result.success(apiResponse)
                    }
                } else {
                    // Log khi response body là null
                    Log.e("AuthRepository", "Empty response body from API")
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                // Nếu response không thành công
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                // Log lỗi chi tiết từ response
                Log.e("AuthRepository", "Registration failed: ${response.code()} - $errorBody")
                Result.failure(Exception("Registration failed: ${response.code()} - $errorBody"))
            }
        } catch (e: Exception) {
            // Log ngoại lệ nếu có
            Log.e("AuthRepository", "An error occurred during registration: ${e.message}", e)
            Result.failure(Exception("An error occurred: ${e.message}", e))
        }
    }



    suspend fun verifyOtp(username: String, email: String, address: String, otp: String,
                          password: String, dateOfBirth: String, phone: String, school: String): Result<ApiResponse> {
        return try {
            val response = apiService.verifyOtp(username, email, address, otp, password, dateOfBirth, phone, school)
            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse != null) {
                    Result.success(apiResponse)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(Exception("Registration failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout(email: String): Result<ApiResponse> {
        return try {
            val response = apiService.logout(email)
            Log.d("AuthRepository", "Logout response: ${response.body()}")
            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse != null) {

                    tokenStoreManager.clearAll()
                    Result.success(apiResponse)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(Exception("Logout failed: ${response.code()}"))
            }
            } catch (e: Exception) {
            Result.failure(e)
        }

    }

}
