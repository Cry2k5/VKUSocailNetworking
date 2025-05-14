package com.dacs3.socialnetworkingvku.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.dacs3.socialnetworkingvku.data.post.requests.PostRequest
import com.dacs3.socialnetworkingvku.data.post.response.CommentResponse
import com.dacs3.socialnetworkingvku.repository.PostRepository
import com.dacs3.socialnetworkingvku.roomdata.post.PostEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository) : ViewModel() {

    // Upload
    private val _uploadState = MutableStateFlow(UploadState.Idle)
    val uploadState: StateFlow<UploadState> = _uploadState.asStateFlow()

    private val _uploadedImageUrl = mutableStateOf<String?>(null)
    val uploadedImageUrl: State<String?> = _uploadedImageUrl

    // Loading + Error + Success
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _isSuccess = mutableStateOf(false)
    val isSuccess: State<Boolean> get() = _isSuccess

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    // Post list from Room
    private val _postList = MutableStateFlow<List<PostEntity>>(emptyList())
    val postList: StateFlow<List<PostEntity>> = _postList.asStateFlow()

    // Comment state
    private val _comments = MutableLiveData<Result<List<CommentResponse>>>()
    val comments: LiveData<Result<List<CommentResponse>>> = _comments

    private val _isCommentSuccess = mutableStateOf(false)
    val isCommentSuccess: State<Boolean> get() = _isCommentSuccess

    private val _isCommentLoading = mutableStateOf(false)
    val isCommentLoading: State<Boolean> get() = _isCommentLoading

    // Call API and store into Room
    fun getAllPosts() {
        _isLoading.value = true
        _errorMessage.value = null
        _isSuccess.value = false

        viewModelScope.launch {
            val result = repository.getAllPostsWithStats()
            _isLoading.value = false

            if (result.isSuccess) {
                _isSuccess.value = true
                observePostsFromRoomData() // Lắng nghe Room
            } else {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Lỗi khi tải bài viết"
            }
        }
    }

    // Room sẽ cập nhật dữ liệu qua LiveData
    fun observePostsFromRoomData() {
        repository.getAllPostsFromRoom().observeForever { posts ->
            _postList.value = posts
        }
    }
    fun getAllPostsFromRoom(): LiveData<List<PostEntity>> {
        return repository.getAllPostsFromRoom()
    }

    // Upload ảnh lên Cloudinary
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

    // Gửi bài viết mới
    fun createPost(content: String, imageUrl: String?) {
        _isLoading.value = true
        _errorMessage.value = null
        _isSuccess.value = false

        viewModelScope.launch {
            try {
                val postRequest = PostRequest(content, imageUrl)
                repository.createPost(postRequest)
                _isSuccess.value = true
                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Lỗi khi tạo bài viết"
                _isLoading.value = false
            }
        }
    }

    // Like / Unlike bài viết
    fun likePost(postId: Long, isCurrentlyLiked: Boolean) {
        viewModelScope.launch {
            val result = repository.likePost(postId)
            if (result.isSuccess) {
                val post = _postList.value.find { it.postId == postId }
                if (post != null) {
                    val newCount = if (isCurrentlyLiked) post.likeCount - 1 else post.likeCount + 1
                    repository.updatePostLikeStatus(postId, !isCurrentlyLiked, newCount)
                }
            }
        }
    }

    // Lấy danh sách bình luận
    fun getCommentsForPost(postId: Long) {
        _isCommentLoading.value = true
        _errorMessage.value = null
        _isCommentSuccess.value = false

        viewModelScope.launch {
            try {
                val result = repository.getCommentsForPost(postId)
                _isCommentLoading.value = false
                if (result.isSuccess) {
                    _comments.value = result
                    _isCommentSuccess.value = true
                } else {
                    _errorMessage.value = result.exceptionOrNull()?.message ?: "Không lấy được bình luận"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Lỗi khi lấy bình luận"
                _isCommentLoading.value = false
            }
        }
    }

    // Gửi bình luận mới
    fun commentPost(postId: Long, content: String) {
        _isLoading.value = true
        _errorMessage.value = null
        _isCommentSuccess.value = false

        viewModelScope.launch {
            try {
                val result = repository.commentPost(postId, content)
                _isLoading.value = false
                _isCommentSuccess.value = result.isSuccess
                if (!result.isSuccess) {
                    _errorMessage.value = result.exceptionOrNull()?.message ?: "Không thể tạo bình luận"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Lỗi khi tạo bình luận"
                _isLoading.value = false
            }
        }
    }

    fun resetState() {
        _uploadState.value = UploadState.Idle
        _isLoading.value = false
        _isSuccess.value = false
        _errorMessage.value = null
        _isCommentSuccess.value = false
        _comments.value = Result.success(emptyList())
    }
}

enum class UploadState {
    Idle,
    Loading,
    Success,
    Error
}
