package com.dacs3.socialnetworkingvku.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.dacs3.socialnetworkingvku.data.post.requests.PostRequest
import com.dacs3.socialnetworkingvku.data.post.response.Post
import com.dacs3.socialnetworkingvku.data.post.response.PostWithStatsResponse
import com.dacs3.socialnetworkingvku.roomdata.post.PostDao
import com.dacs3.socialnetworkingvku.roomdata.post.PostEntity
import com.dacs3.socialnetworkingvku.roomdata.post.toEntity
import com.dacs3.socialnetworkingvku.security.TokenStoreManager
import com.dacs3.socialnetworkingvku.testApi.ApiService
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class PostRepository(
    private val apiService: ApiService,
    private val postDao: PostDao,
    private val tokenStoreManager: TokenStoreManager
    ) {
    suspend fun getAllPostsWithStats(): Result<List<PostWithStatsResponse>> {
        return try {
            val response = apiService.getAllPostsForHome()
            if (response.isSuccessful) {
                Log.d("PostRepository", "Response body: ${response.body()}")
                val posts = response.body()
                if (posts != null) {
                    // Xóa tất cả các bài viết trong Room trước khi lưu mới
                    postDao.clearPosts()

                    // Chuyển đổi các bài viết thành PostEntity và lưu vào Room
                    val entities = posts.map { it.toEntity() }
                    postDao.insertAll(entities)

                    Log.d("PostRepository", "Posts saved to DB: $entities")

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

            val response = apiService.createPost(token, postRequest)
            if (response.isSuccessful) {
                val post = response.body()
                if (post != null) {
                    Result.success(post)
                } else {
                    Result.failure(Exception("Phản hồi thành công nhưng không có nội dung."))
                }
            } else {
                Result.failure(Exception("Tạo bài viết thất bại: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    suspend fun getAllPostsFromRoom(): List<PostEntity> {
        return postDao.getAllPosts()
    }
    // Xóa tất cả bài đăng trong Room
    suspend fun clearPostsFromRoom() {
        postDao.clearPosts()
    }



    // Cập nhật bài đăng trong Room

}