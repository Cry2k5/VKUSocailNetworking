package com.dacs3.socialnetworkingvku.data.user.requests

import java.time.LocalDate

data class UserUpdateRequest (
    val name: String,
    val address: String?,
    val dateOfBirth: String?,
    val bio: String?,
    val school: String?,
    val avatar: String?,
    val phoneNumber: String?,
)