package com.dacs3.socialnetworkingvku.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dacs3.socialnetworkingvku.repository.PostRepository
import androidx.lifecycle.viewModelScope
import com.dacs3.socialnetworkingvku.data.post.requests.PostRequest
import com.dacs3.socialnetworkingvku.roomdata.post.PostEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository) : ViewModel() {

    private val _uploadState = MutableStateFlow(UploadState.Idle)
    val uploadState: StateFlow<UploadState> = _uploadState.asStateFlow()

    private val _uploadedImageUrl = mutableStateOf<String?>(null)
    val uploadedImageUrl: State<String?> = _uploadedImageUrl

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _isSuccess = mutableStateOf(false)
    val isSuccess: State<Boolean> get() = _isSuccess

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> get() = _errorMessage

    private val _isDataLoaded = mutableStateOf(true)
    val isDataLoaded: State<Boolean> get() = _isDataLoaded

    private val _postList = MutableStateFlow<List<PostEntity>>(emptyList())
    val postList: StateFlow<List<PostEntity>> = _postList

    private val _isLikeSuccess = mutableStateOf(false)
    val isLikeSuccess: State<Boolean> get() = _isLikeSuccess


    fun getAllPosts() {
        _isLoading.value = true
        _errorMessage.value = null
        _isSuccess.value = false

        viewModelScope.launch {
            Log.d("PostViewModel", "Fetching posts...")

            val result = repository.getAllPostsWithStats()

            _isLoading.value = false

            if (result.isSuccess) {
                _isSuccess.value = true
                _isDataLoaded.value = true
            } else {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Lấy danh sách bài viết thất bại"
            }
        }
    }

    // Lấy dữ liệu bài viết từ Room
    fun getAllPostsFromRoomData() {
        viewModelScope.launch {
            // Lấy dữ liệu từ Room
            val data = repository.getAllPostsFromRoom()

            // Log dữ liệu từ Room
            Log.d("PostViewModel", "Loaded from Room: $data")

            // Cập nhật dữ liệu vào _postList
            _postList.value = data
        }
    }
    fun uploadImage(imageUri: Uri, context: Context) {
        viewModelScope.launch {
            _uploadState.value = UploadState.Loading
            val result = repository.uploadImageToCloudinary(imageUri, context)
            if (result != null) {
                _uploadedImageUrl.value = result
                _uploadState.value = UploadState.Success
            } else {
                _uploadState.value = UploadState.Error
            }
        }
    }

    fun createPost(content: String, imageUrl: String?) {
        _isLoading.value = true
        _errorMessage.value = null
        _isSuccess.value = false
        viewModelScope.launch {
            try {
                Log.d("PostViewModel", "Creating post...")
                val postRequest = PostRequest(content, imageUrl)
                repository.createPost(postRequest)
                _isLoading.value = false
                _isSuccess.value = true
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Có lỗi xảy ra khi tạo bài viết"
            }
        }
    }

    fun likePost(postId: Long) {
        _isLoading.value = true
        _errorMessage.value = null
        _isLikeSuccess.value = false

        viewModelScope.launch {
            try {
                Log.d("PostViewModel", "Starting to like post with ID: $postId")
                val result = repository.likePost(postId)
                _isLoading.value = false

                if (result.isSuccess) {
                    _isLikeSuccess.value = true
                    Log.d("PostViewModel", "Successfully liked post with ID: $postId")
                } else {
                    _errorMessage.value = result.exceptionOrNull()?.message ?: "Error while liking the post."
                    Log.e("PostViewModel", "Failed to like post with ID: $postId", result.exceptionOrNull())
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Có lỗi xảy ra khi like bài viết"
                _isLoading.value = false
                Log.e("PostViewModel", "Error liking post with ID: $postId", e)
            }
        }
    }


    fun resetState() {
        _uploadState.value = UploadState.Idle
        _isLoading.value = false
        _isSuccess.value = false
        _errorMessage.value = null
        _isDataLoaded.value = false // Đặt lại cờ sau khi dữ liệu đã được tải
    }

}
enum class UploadState {
    Idle,      // Chưa làm gì
    Loading,   // Đang upload
    Success,   // Upload thành công
    Error      // Upload thất bại
}