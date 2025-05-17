package com.dacs3.socialnetworkingvku.viewmodel

import GeminiRequest
import GeminiResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.socialnetworkingvku.repository.GeminiRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class GeminiViewModel(private val repository: GeminiRepository) : ViewModel() {

    // Sử dụng State để lưu trữ phản hồi từ API
    private val _aiContent = mutableStateOf<String?>(null)
    val aiContent: State<String?> get() = _aiContent

    // Xử lý khi gọi API
    fun generateContent(request: GeminiRequest) {
        viewModelScope.launch {
            try {
                // Gọi đến repository để lấy phản hồi từ API
                val response: GeminiResponse = repository.generateContent(request)
                // Cập nhật giá trị aiContent với dữ liệu trả về từ API
                _aiContent.value = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
            } catch (e: Exception) {
                // Nếu có lỗi, cập nhật giá trị để thông báo lỗi
                _aiContent.value = "Không thể kết nối đến AI"
            }
        }
    }
}
