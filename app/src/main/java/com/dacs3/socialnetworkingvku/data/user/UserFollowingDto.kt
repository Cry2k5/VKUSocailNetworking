package com.dacs3.socialnetworkingvku.data.user

data class UserFollowingDto (
    val userId: Long,
    val username: String,
    val avatar :String?,
    val lastMessage:String?,
    val lastMessageSenderId: Long?,
    val lastMessageTimestamp: Long?
    )