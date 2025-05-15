package com.dacs3.socialnetworkingvku.repository

import com.dacs3.socialnetworkingvku.data.message.Message
import com.dacs3.socialnetworkingvku.security.TokenStoreManager
import com.dacs3.socialnetworkingvku.testApi.ChatApiService
import kotlinx.coroutines.flow.first

class ChatRepository(
    private val chatApiService: ChatApiService,
    private val tokenStoreManager: TokenStoreManager
) {

    suspend fun getMessages(receiverId: Long): Result<List<Message>>{
        return try {
            val token = "Bearer ${tokenStoreManager.accessTokenFlow.first()}"
            val response = chatApiService.getMessages(token, receiverId)

            if (response.isSuccessful) {
                val messages = response.body()
                if (messages != null) {
                    Result.success(messages)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                    Result.failure(Exception("Failed to fetch messages: ${response.code()}"))
                }
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }

}

