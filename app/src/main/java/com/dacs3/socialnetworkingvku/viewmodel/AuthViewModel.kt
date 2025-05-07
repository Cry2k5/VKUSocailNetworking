package com.dacs3.socialnetworkingvku.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.socialnetworkingvku.data.User
import com.dacs3.socialnetworkingvku.data.requests.RegisterRequest
import com.dacs3.socialnetworkingvku.repository.AuthRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _isSuccess = mutableStateOf(false)
    val isSuccess: State<Boolean> get() = _isSuccess

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> get() = _errorMessage

    private val _pendingRegisterData = mutableStateOf<RegisterRequest?>(null)
    val pendingRegisterData: RegisterRequest? get() = _pendingRegisterData.value

    // Phương thức lưu trữ dữ liệu đăng ký
    fun cacheRegisterData(request: RegisterRequest) {
        Log.d("AuthViewModel", "Caching register data: $request")
        _pendingRegisterData.value = request
    }

    fun login(email: String, password: String) {
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


    fun resetStates() {
        _isLoading.value = false
        _isSuccess.value = false
        _errorMessage.value = null
    }


}

