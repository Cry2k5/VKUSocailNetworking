package com.dacs3.socialnetworkingvku.roomdata.post

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostDao {


    // Phương thức chèn tất cả bài viết vào DB
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<PostEntity>)

    // Phương thức lấy tất cả bài viết
    @Query("SELECT * FROM posts ORDER BY createdAt DESC")
    suspend fun getAllPosts(): List<PostEntity>

    // Phương thức xóa tất cả bài viết
    @Query("DELETE FROM posts")
    suspend fun clearPosts()
}
