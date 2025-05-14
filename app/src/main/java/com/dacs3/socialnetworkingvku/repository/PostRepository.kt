package com.dacs3.socialnetworkingvku.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.dacs3.socialnetworkingvku.data.auth.response.ApiResponse
import com.dacs3.socialnetworkingvku.data.post.requests.PostRequest
import com.dacs3.socialnetworkingvku.data.post.response.CommentResponse
import com.dacs3.socialnetworkingvku.data.post.response.Post
import com.dacs3.socialnetworkingvku.data.post.response.PostWithStatsResponse
import com.dacs3.socialnetworkingvku.roomdata.post.PostDao
import com.dacs3.socialnetworkingvku.roomdata.post.PostEntity
import com.dacs3.socialnetworkingvku.roomdata.post.toEntity
import com.dacs3.socialnetworkingvku.security.TokenStoreManager
import com.dacs3.socialnetworkingvku.testApi.PostApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.io.ByteArrayOutputStream

class PostRepository(
    private val postApiService: PostApiService,
    private val postDao: PostDao,
    private val tokenStoreManager: TokenStoreManager
    ) {
    suspend fun getAllPostsWithStats(): Result<List<PostWithStatsResponse>> {
        return try {
            val tokenDataStore = tokenStoreManager.accessTokenFlow.first()
            val token = "Bearer $tokenDataStore"
            Log.d("PostRepository", "Token to get post:"+ token)
            val response = postApiService.getAllPostsForHome(token)
            if (response.isSuccessful) {
                Log.d("PostRepository", "Response body: ${response.body()}")
                val posts = response.body()
                if (posts != null) {
                    // Xóa tất cả các bài viết trong Room trước khi lưu mới
                    Log.d("PostRepository", "Clearing posts from DB")
                    postDao.clearPosts()

                    Log.d("PostRepository", "Posts to save: $posts")
                    // Chuyển đổi các bài viết thành PostEntity và lưu vào Room
                    val entities = posts.map { it.toEntity() }
                    postDao.insertAll(entities)

                    Log.d("PostRepository", "Posts saved to DB")
                    Log.d("PostRepository", "Posts from DB: $entities")
                    // Trả về kết quả thành công
                    Result.success(posts)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(Exception("Failed to fetch posts: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createPost(postRequest: PostRequest): Result<Post> {
        return try {
            val tokenDataStore = tokenStoreManager.accessTokenFlow.first()
            val token = "Bearer $tokenDataStore"

            // Gửi yêu cầu tạo bài viết
            val response = postApiService.createPost(token, postRequest)

            // Kiểm tra phản hồi có thành công không
            if (response.isSuccessful) {
                val post = response.body()
                Log.d("PostRepository", "Response body: $post")  // Log dữ liệu trả về

                if (post != null) {
                    Result.success(post)
                } else {
                    // Log nếu body trả về là null
                    Log.e("PostRepository", "Phản hồi thành công nhưng không có nội dung.")
                    Result.failure(Exception("Phản hồi thành công nhưng không có nội dung."))
                }
            } else {
                // Log khi phản hồi không thành công
                Log.e(
                    "PostRepository",
                    "Tạo bài viết thất bại: ${response.code()} ${response.message()}"
                )
                Result.failure(Exception("Tạo bài viết thất bại: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            // Log khi có lỗi trong quá trình gọi API
            Log.e("PostRepository", "Exception occurred: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun updatePostLikeStatus(postId: Long, isLiked: Boolean, likeCount: Int) {
        // Cập nhật trạng thái Like và LikeCount vào database
        postDao.updatePostLikeStatus(postId, isLiked, likeCount)
    }
    suspend fun likePost(postId: Long): Result<ApiResponse> {
        return try {
            val tokenDataStore = tokenStoreManager.accessTokenFlow.first()
            val token = "Bearer $tokenDataStore"

            // Gọi API bất đồng bộ, Retrofit sẽ tự động xử lý việc này trong nền
            val response = postApiService.likePost(token, postId)

            // Kiểm tra phản hồi từ server
            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse != null) {
                    Result.success(apiResponse)  // Trả về kết quả thành công
                } else {
                    Result.failure(Exception("Empty response body"))  // Nếu không có body
                }
            } else {
                // Nếu không thành công, lấy thông tin chi tiết từ errorBody
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("Failed to like post: ${response.code()} - $errorBody"))
            }
        } catch (e: Exception) {
            // Ghi lại lỗi (nếu có) khi gọi API
            Log.e("likePost", "Error liking post with ID: $postId", e)
            Result.failure(e)  // Trả về lỗi nếu có exception
        }
    }

    suspend fun getCommentsForPost(postId: Long): Result<List<CommentResponse>> {
        return try {
            val response = postApiService.getCommentsForPost(postId)
            if (response.isSuccessful) {
                val comments = response.body()
                if (comments != null) {
                    Result.success(comments)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(Exception("Failed to fetch comments: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun commentPost(postId: Long, content: String): Result<ApiResponse> {

        return try {
            val tokenDataStore = tokenStoreManager.accessTokenFlow.first()
            val token = "Bearer $tokenDataStore"
            val response = postApiService.commentPost(token, postId, content)
            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse != null) {
                    Result.success(apiResponse)
                    } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(Exception("Failed to comment post: ${response.code()}"))
        }
            } catch (e: Exception) {
            Result.failure(e)
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

                // Upload ảnh lên Cloudinary với các tham số resize và folder
                MediaManager.get().upload(imageBytes)
                    .option("resource_type", "image")
                    .option("upload_preset", "ml_default")
                    .option("folder", "posts")  // Thêm tham số để upload vào folder 'posts'
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


    fun getAllPostsFromRoom(): LiveData<List<PostEntity>> {
        return postDao.getAllPosts()
    }

    // Xóa tất cả bài đăng trong Room
    suspend fun clearPostsFromRoom() {
        postDao.clearPosts()
    }



    // Cập nhật bài đăng trong Room

}