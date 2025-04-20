package com.dacs3.socialnetworkingvku.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.socialnetworkingvku.data.Post
import com.dacs3.socialnetworkingvku.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    private val repository = PostRepository()

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            try {
                val response = repository.getPosts()
                _posts.value = response
            } catch (e: Exception) {
                // handle error (log, toast, etc)
            }
        }
    }
}
