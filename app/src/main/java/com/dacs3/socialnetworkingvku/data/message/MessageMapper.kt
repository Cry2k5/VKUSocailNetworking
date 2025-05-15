package com.dacs3.socialnetworkingvku.data.message

fun Message.toMessageDTO(): MessageDTO {
    return MessageDTO(
        senderId = this.sender.userId,
        receiverId = this.receiver?.userId ?: 0L,
        content = this.content,
        image = this.image,
        video = this.video,
    )
}