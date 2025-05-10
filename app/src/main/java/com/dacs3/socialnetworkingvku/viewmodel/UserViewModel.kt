package com.dacs3.socialnetworkingvku.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.socialnetworkingvku.data.auth.requests.RegisterRequest
import com.dacs3.socialnetworkingvku.data.user.User
import com.dacs3.socialnetworkingvku.data.user.UserDto
import com.dacs3.socialnetworkingvku.data.user.requests.UserUpdateRequest
import com.dacs3.socialnetworkingvku.repository.UserRepository
import com.dacs3.socialnetworkingvku.security.TokenStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository,
    private val tokenStoreManager: TokenStoreManager
) : ViewModel() {

    private val _uploadState = MutableStateFlow(UploadAvatarState.Idle)
    val uploadState: StateFlow<UploadAvatarState> = _uploadState.asStateFlow()

    private val _uploadedImageUrl = mutableStateOf<String?>(null)
    val uploadedImageUrl: State<String?> = _uploadedImageUrl

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _isSuccess = mutableStateOf(false)
    val isSuccess: State<Boolean> get() = _isSuccess

    private val _isUpdateSuccess = mutableStateOf(false)
    val isUpdateSuccess: State<Boolean> get() = _isUpdateSuccess

    private val _isDeleteSuccess = mutableStateOf(false)
    val isDeleteSuccess: State<Boolean> get() = _isDeleteSuccess

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> get() = _errorMessage
    val userDtoFlow: Flow<UserDto> = tokenStoreManager.userDtoFlow

    fun getInfo() {
        _isLoading.value = true
        _errorMessage.value = null
        _isSuccess.value = false
        viewModelScope.launch {
            try {
                val result = repository.getUserInfo()

                _isLoading.value = false
                if (result.isSuccess) {
                    _isSuccess.value = true
                } else {
                    _errorMessage.value =
                        result.exceptionOrNull()?.message ?: "Lấy thông tin thất bại"
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _errorMessage.value = e.message ?: "Đã xảy ra lỗi"
            }
        }
    }

    fun updateUser(request: UserUpdateRequest) {
        _isLoading.value = true
        _errorMessage.value = null
        _isUpdateSuccess.value = false

        viewModelScope.launch {
            try {
                val result = repository.updateUser(request)
                _isLoading.value = false
                if (result.isSuccess) {
                    _isUpdateSuccess.value = true
                } else {
                    _errorMessage.value = result.exceptionOrNull()?.message ?: "Cập nhật thất bại"
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _errorMessage.value = e.message ?: "Đã xảy ra lỗi"
            }
        }
    }

    fun deleteAccount() {
        _isLoading.value = true
        _errorMessage.value = null
        _isDeleteSuccess.value = false
        viewModelScope.launch {
            try {
                val result = repository.deleteUser()

                _isLoading.value = false
                if (result.isSuccess) {
                    _isDeleteSuccess.value = true
                } else {
                    _errorMessage.value =
                        result.exceptionOrNull()?.message ?: "Xóa tài khoản thất bại"
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _errorMessage.value = e.message ?: "Đã xảy ra lỗi"

            }
        }
    }

    fun uploadImage(imageUri: Uri, context: Context) {
        viewModelScope.launch {
            _uploadState.value = UploadAvatarState.Loading
            val result = repository.uploadAvatarToCloudinary(imageUri, context)
            if (result != null) {
                _uploadedImageUrl.value = result
                _uploadState.value = UploadAvatarState.Success
            } else {
                _uploadState.value = UploadAvatarState.Error
            }
        }
    }

    fun resetStates() {
        _isLoading.value = false
        _isSuccess.value = false
        _errorMessage.value = null
        _isUpdateSuccess.value = false
        _isDeleteSuccess.value = false
    }
}

enum class UploadAvatarState {
    Idle,      // Chưa làm gì
    Loading,   // Đang upload
    Success,   // Upload thành công
    Error      // Upload thất bại
}

