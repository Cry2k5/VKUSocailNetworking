package com.dacs3.socialnetworkingvku.repository

import GeminiRequest
import GeminiResponse
import GeminiService

class GeminiRepository(private val geminiService: GeminiService) {

    suspend fun generateContent(request: GeminiRequest): GeminiResponse {
        val endpoint = "v1beta/models/gemini-2.0-flash:generateContent"
        return geminiService.generateContent(endpoint, request)
    }
}