package com.dacs3.socialnetworkingvku.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.socialnetworkingvku.data.user.User
import com.dacs3.socialnetworkingvku.data.auth.requests.RegisterRequest
import com.dacs3.socialnetworkingvku.data.user.UserDto
import com.dacs3.socialnetworkingvku.repository.AuthRepository
import com.dacs3.socialnetworkingvku.security.TokenStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository, private val tokenStoreManager: TokenStoreManager) : ViewModel() {
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _isSuccess = mutableStateOf(false)
    val isSuccess: State<Boolean> get() = _isSuccess

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> get() = _errorMessage

    private val _pendingRegisterData = mutableStateOf<RegisterRequest?>(null)
    val pendingRegisterData: RegisterRequest? get() = _pendingRegisterData.value

    val user: Flow<User> = tokenStoreManager.userFlow
    val userDto: Flow<UserDto> = tokenStoreManager.userDtoFlow

    private val _isForgotSuccess = mutableStateOf(false)
    val isForgotSuccess: State<Boolean> = _isForgotSuccess

    private val _forgotErrorMessage = mutableStateOf<String?>(null)
    val forgotErrorMessage: State<String?> = _forgotErrorMessage

    private val _isOtpVerified = mutableStateOf(false)
    val isOtpVerified: State<Boolean> = _isOtpVerified

    private val _otpErrorMessage = mutableStateOf<String?>(null)
    val otpErrorMessage: State<String?> = _otpErrorMessage

    // Phương thức lưu trữ dữ liệu đăng ký
    fun cacheRegisterData(request: RegisterRequest) {
        Log.d("AuthViewModel", "Caching register data: $request")
        _pendingRegisterData.value = request
    }

    fun login(email: String, password: String) {
        Log.d("AuthViewModel", "Login with email: $email, password: $password")
        _isLoading.value = true
        _errorMessage.value = null
        _isSuccess.value = false

        viewModelScope.launch {
            val result = repository.login(email, password)
            _isLoading.value = false
            if (result.isSuccess) {
                _isSuccess.value = true
            } else {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Đăng nhập thất bại"
            }
        }
    }

    fun register(request: RegisterRequest) {
        _isLoading.value = true
        _errorMessage.value = null
        _isSuccess.value = false

        viewModelScope.launch {
            try {
                val result = repository.register(request)
                _isLoading.value = false
                Log.d("AuthViewModel", "Register result: $_isSuccess}")
                // Kiểm tra kết quả của việc gọi API
                if (result.isSuccess) {
                    // Cập nhật trạng thái thành công khi kết quả là success
                    _isSuccess.value = true
                    Log.d("AuthViewModel", "Register result: $_isSuccess}")
                } else {
                    // Nếu thất bại, hiển thị thông báo lỗi
                    _errorMessage.value = result.exceptionOrNull()?.message ?: "Đăng ký thất bại"
                }
            } catch (e: Exception) {
                _isLoading.value = false
                // Lưu lại thông báo lỗi nếu có exception
                _errorMessage.value = e.message ?: "Đã xảy ra lỗi"
            }
        }
    }



    fun verifyOtp(username: String, email: String, address: String, otp: String,
                  password: String, dateOfBirth: String, phone: String, school: String){

        _isLoading.value = true
        _errorMessage.value = null
        _isSuccess.value = false

        viewModelScope.launch {
            val result = repository.verifyOtp(username, email, address, otp, password, dateOfBirth, phone, school)
            _isLoading.value = false
            if(result.isSuccess){
                _isSuccess.value = true
            }else {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Xác thực thất bại"
            }
        }
    }
    fun forgotPassword(email: String) {
        _isLoading.value = true
        _forgotErrorMessage.value = null
        _isForgotSuccess.value = false

        viewModelScope.launch {
            val result = repository.forgotPassword(email)
            Log.d("AuthViewModel", "Forgot password result: $result")
            _isLoading.value = false

            if (result.isSuccess) {
                _isForgotSuccess.value = true
            } else {
                _forgotErrorMessage.value = result.exceptionOrNull()?.message ?: "Quên mật khẩu thất bại"
            }
        }
    }


    fun verifyOtpPassword(email: String, otp: String) {
        _isLoading.value = true
        _otpErrorMessage.value = null
        _isOtpVerified.value = false

        viewModelScope.launch {
            Log.d("AuthViewModel", "Verifying OTP with email: $email, OTP: $otp")
            val result = repository.verifyOtpPassword(email, otp)
            _isLoading.value = false

            if (result.isSuccess) {
                _isOtpVerified.value = true
            } else {
                _otpErrorMessage.value = result.exceptionOrNull()?.message ?: "Xác minh OTP thất bại"
            }
        }
    }

    fun logout(email: String){
        _isLoading.value = true
        _errorMessage.value = null
        _isSuccess.value = false
        viewModelScope.launch {
            val result = repository.logout(email)

            _isLoading.value = false
            if(result.isSuccess){
                _isSuccess.value = true
            }else {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Đăng xuất thất bại"
            }
        }
    }


    fun resetStates() {
        _isLoading.value = false
        _isSuccess.value = false
        _errorMessage.value = null
    }


}

