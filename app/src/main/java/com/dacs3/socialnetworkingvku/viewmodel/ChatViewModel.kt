package com.dacs3.socialnetworkingvku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dacs3.socialnetworkingvku.data.MessageDTO

class ChatViewModel {
    private val _messages = MutableLiveData<List<MessageDTO>>()
    val messages: LiveData<List<MessageDTO>> = _messages


    fun getCurrentUser(){

    }
}