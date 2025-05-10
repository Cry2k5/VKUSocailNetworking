package com.dacs3.socialnetworkingvku.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.socialnetworkingvku.data.auth.requests.RegisterRequest
import com.dacs3.socialnetworkingvku.data.user.requests.UserUpdateRequest
import com.dacs3.socialnetworkingvku.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) :ViewModel(){

    private val _uploadedImageUrl = mutableStateOf<String?>(null)
    val uploadedImageUrl: State<String?> = _uploadedImageUrl

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _isSuccess = mutableStateOf(false)
    val isSuccess: State<Boolean> get() = _isSuccess

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> get() = _errorMessage

    fun updateUser(request: UserUpdateRequest)
    {
        _isLoading.value = true
        _errorMessage.value = null
        _isSuccess.value = false

        viewModelScope.launch {
            try {
                val result = repository.updateUser(request)
                _isLoading.value = false
                if(result.isSuccess){
                    _isSuccess.value = true
                }else{
                    _errorMessage.value = result.exceptionOrNull()?.message ?: "Cập nhật thất bại"
                }
            }catch (e: Exception){
                _isLoading.value = false
                _errorMessage.value = e.message ?: "Đã xảy ra lỗi"
            }
        }
    }
}