package com.dacs3.socialnetworkingvku.data.user

data class UserDto (
    val id: Long,
    val email: String,
    val name: String,
    val address: String?,
    val dateOfBirth: String?,
    val bio: String?,
    val school: String?,
    val avatar: String?,
    val phoneNumber: String?,

    )

