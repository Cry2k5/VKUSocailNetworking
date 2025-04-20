package com.dacs3.socialnetworkingvku.repository

import com.dacs3.socialnetworkingvku.data.Post
import com.dacs3.socialnetworkingvku.testApi.RetrofitClient
import retrofit2.Response

class PostRepository {
    suspend fun getPosts(): List<Post> {
        val response: Response<List<Post>> = RetrofitClient.api.getPosts()

        // Kiểm tra xem phản hồi có thành công không
        if (response.isSuccessful) {
            // Trả về danh sách bài viết
            return response.body() ?: emptyList() // Nếu body là null, trả về danh sách rỗng
        } else {
            // Xử lý lỗi nếu phản hồi không thành công
            throw Exception("Error: ${response.message()}")
        }
    }
}
