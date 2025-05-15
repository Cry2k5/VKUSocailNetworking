package com.dacs3.socialnetworkingvku.data.message

import com.dacs3.socialnetworkingvku.data.user.UserDto

data class Message(
    val messageId:Long,
    val sender: UserDto,  // ID người gửi
    val receiver: UserDto?,  // ID người nhận (có thể null)
    val content: String?,  // Nội dung tin nhắn
    val image: String?,  // Đường dẫn đến ảnh đính kèm (có thể null)
    val video: String?, // Đường dẫn đến video đính kèm (có thể null)
    val createAt: String,
)