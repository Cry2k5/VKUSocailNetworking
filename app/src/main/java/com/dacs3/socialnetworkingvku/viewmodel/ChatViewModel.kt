package com.dacs3.socialnetworkingvku.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.socialnetworkingvku.data.message.Message
import com.dacs3.socialnetworkingvku.data.message.MessageDTO
import com.dacs3.socialnetworkingvku.data.post.response.PostWithStatsResponse
import com.dacs3.socialnetworkingvku.repository.ChatRepository
import com.dacs3.socialnetworkingvku.repository.PostRepository
import kotlinx.coroutines.launch

class ChatViewModel(private val repository: ChatRepository) : ViewModel() {

    private val _messages = MutableLiveData<Map<Long, List<Message>>>()
    val messages: LiveData<Map<Long, List<Message>>> = _messages

    fun getMessages(receiverId: Long) {
        viewModelScope.launch {
            val result = repository.getMessages(receiverId)
            if (result.isSuccess) {
                val newMap = _messages.value.orEmpty().toMutableMap()
                newMap[receiverId] = result.getOrThrow()
                _messages.postValue(newMap)
            }
        }
    }

    fun clearMessages(receiverId: Long) {
        val updated = _messages.value.orEmpty().toMutableMap()
        updated.remove(receiverId)
        _messages.postValue(updated)
    }
}
