package com.dacs3.socialnetworkingvku.roomdata.post

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {


    // Phương thức chèn tất cả bài viết vào DB
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<PostEntity>)

    @Query("SELECT * FROM posts ORDER BY createdAt DESC")
    fun getAllPosts(): LiveData<List<PostEntity>>


    @Query("UPDATE posts SET isLiked = :isLiked, likeCount = :likeCount WHERE postId = :postId")
    suspend fun updatePostLikeStatus(postId: Long, isLiked: Boolean, likeCount: Int)

    // Phương thức xóa tất cả bài viết
    @Query("DELETE FROM posts")
    suspend fun clearPosts()
}
