package com.dacs3.socialnetworkingvku.testApi

import com.dacs3.socialnetworkingvku.data.message.Message
import com.dacs3.socialnetworkingvku.data.message.MessageDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ChatApiService {
    @GET("message/{receiverId}")
    suspend fun getMessages(
        @Header("Authorization") authorization: String,
        @Path("receiverId") receiverId: Long
    ): Response<List<Message>>

}