package com.dacs3.socialnetworkingvku.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.dacs3.socialnetworkingvku.data.post.requests.PostRequest
import com.dacs3.socialnetworkingvku.data.post.response.CommentResponse
import com.dacs3.socialnetworkingvku.data.post.response.PostWithStatsResponse
import com.dacs3.socialnetworkingvku.data.user.UserFollowingDto
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

    private val _isLikeLoading = mutableStateOf(false)
    val isLikeLoading: State<Boolean> get() = _isLikeLoading

    private val _isLikeSuccess = mutableStateOf(false)
    val isLikeSuccess: State<Boolean> get() = _isLikeSuccess

    private val _postListNoRoomData = MutableLiveData<List<PostWithStatsResponse>>()
    val postListNoRoomData: LiveData<List<PostWithStatsResponse>> = _postListNoRoomData


    fun getPostsForHomeScreen() {
        _isLoading.value = true  // Bắt đầu tải dữ liệu
        viewModelScope.launch {
            try {
                val result = repository.getPostForHomeScreen()
                if (result.isSuccess) {
                    _postListNoRoomData.value = result.getOrThrow()  // Cập nhật dữ liệu thành công
                } else {
                    _errorMessage.value = "Không thể tải bài viết"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Lỗi khi tải dữ liệu: ${e.message}"  // Xử lý lỗi
            } finally {
                _isLoading.value = false  // Kết thúc tải dữ liệu
            }
        }
    }
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

    fun likePost(postId: Long) {
        viewModelScope.launch {
            // Lấy danh sách bài viết hiện tại từ _postListNoRoomData
            val currentPosts = _postListNoRoomData.value?.toMutableList() ?: mutableListOf()

            // Gọi API để like/unlike bài viết
            val result = repository.likePost(postId)

            // Kiểm tra kết quả từ API
            if (result.isSuccess) {
                // Tìm bài viết cần thay đổi trạng thái
                val post = currentPosts.find { it.post.post_id == postId }
                post?.let {
                    // Cập nhật trạng thái like và số lượt like
                    it.isLiked = !it.isLiked
                    it.likeCount += if (it.isLiked) 1 else -1
                }

                // Cập nhật lại _postListNoRoomData để UI nhận thông tin mới
                _postListNoRoomData.value = currentPosts
            } else {
                // Xử lý trường hợp thất bại (thông báo lỗi cho người dùng nếu cần)
                val errorMessage = result.exceptionOrNull()?.message ?: "Unknown error"
                Log.e("PostViewModel", "Error: $errorMessage")

                // Bạn có thể dùng LiveData để hiển thị thông báo lỗi cho UI nếu cần
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
        _isCommentLoading.value = false
        _isLikeLoading.value = false
        _isLikeSuccess.value = false
    }

    fun resetStateForLike(resetPosts: Boolean = false) {
        _uploadState.value = UploadState.Idle
        _isLoading.value = false
        _isSuccess.value = false
        _errorMessage.value = null
        _isCommentSuccess.value = false
        _comments.value = Result.success(emptyList())
        _isCommentLoading.value = false
        _isLikeLoading.value = false
        _isLikeSuccess.value = false

        // Reset danh sách bài viết chỉ khi cần thiết
        if (resetPosts) {
            _postListNoRoomData.value = emptyList()
        }
    }

}

enum class UploadState {
    Idle,
    Loading,
    Success,
    Error
}
