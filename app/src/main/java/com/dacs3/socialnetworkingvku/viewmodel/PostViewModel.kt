package com.dacs3.socialnetworkingvku.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dacs3.socialnetworkingvku.repository.PostRepository
import androidx.lifecycle.viewModelScope
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.dacs3.socialnetworkingvku.data.post.requests.PostRequest
import com.dacs3.socialnetworkingvku.roomdata.post.PostEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.InputStream

class PostViewModel(private val repository: PostRepository) : ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _isSuccess = mutableStateOf(false)
    val isSuccess: State<Boolean> get() = _isSuccess

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> get() = _errorMessage

    private val _isDataLoaded = mutableStateOf(false)
    val isDataLoaded: State<Boolean> get() = _isDataLoaded

    private val _postList = MutableStateFlow<List<PostEntity>>(emptyList())
    val postList: StateFlow<List<PostEntity>> = _postList

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
    suspend fun uploadImageToCloudinary(imageUri: Uri, context: Context): String? =
        kotlinx.coroutines.suspendCancellableCoroutine { continuation ->
            try {
                // Chuyển Uri thành ByteArray
                val imageBytes = uriToByteArray(context, imageUri)
                if (imageBytes == null) {
                    continuation.resume(null, null)
                    return@suspendCancellableCoroutine
                }

                // Upload ảnh lên Cloudinary
                MediaManager.get().upload(imageBytes)
                    .option("resource_type", "image")
                    .option("upload_preset", "ml_default") // Sử dụng unsigned preset
                    .callback(object : UploadCallback {
                        override fun onStart(requestId: String?) {}

                        override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {}

                        override fun onSuccess(requestId: String?, resultData: Map<*, *>?) {
                            val url = resultData?.get("secure_url") as? String
                            continuation.resume(url, null)
                        }

                        override fun onError(requestId: String?, error: ErrorInfo?) {
                            continuation.resume(null, null)
                        }

                        override fun onReschedule(requestId: String?, error: ErrorInfo?) {
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
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val byteArrayOutputStream = ByteArrayOutputStream()
            inputStream?.copyTo(byteArrayOutputStream)
            byteArrayOutputStream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            null
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
                val result = repository.createPost(postRequest)
                if (result.isSuccess) {
                    Log.d("PostViewModel", "Post created successfully!")
                    _isLoading.value = false
                    _isSuccess.value = true
                } else {
                    _errorMessage.value = (result.exceptionOrNull() ?: "Tạo bài viết thất bại").toString()
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Có lỗi xảy ra khi tạo bài viết"
            }
        }
    }

    fun resetState() {
        _isLoading.value = false
        _isSuccess.value = false
        _errorMessage.value = null
        _isDataLoaded.value = false // Đặt lại cờ sau khi dữ liệu đã được tải
    }

}
