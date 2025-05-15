package com.dacs3.socialnetworkingvku.websocket

import android.util.Log
import com.dacs3.socialnetworkingvku.data.message.MessageDTO
import com.google.gson.Gson
import okhttp3.*

class WebSocketManager(
    private val userId: Long,
    private val onMessageReceived: (MessageDTO) -> Unit
) {
    private var webSocket: WebSocket? = null
    private val client = OkHttpClient()
    private val gson = Gson()

    fun connect(serverUrl: String = "ws://192.168.2.88:8080/ws/chat") {
        val request = Request.Builder()
            .url("$serverUrl?userId=$userId")
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d("WebSocket", "✅ Connected to WebSocket")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                try {
                    val message = gson.fromJson(text, MessageDTO::class.java)
                    onMessageReceived(message)
                    Log.d("WebSocket", "📩 Received: $text")
                } catch (e: Exception) {
                    Log.e("WebSocket", "❌ Error parsing message: ${e.message}")
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e("WebSocket", "❌ Connection failed: ${t.message}")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, null)
                Log.d("WebSocket", "🔌 Connection closing: $code $reason")
            }
        })
    }

    fun sendMessage(message: MessageDTO) {
        val messageJson = gson.toJson(message)
        webSocket?.send(messageJson)
    }

    fun disconnect() {
        webSocket?.close(1000, "User disconnected")
        webSocket = null
    }
}
