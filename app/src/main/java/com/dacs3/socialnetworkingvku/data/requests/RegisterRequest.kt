package com.dacs3.socialnetworkingvku.data.requests

import java.time.LocalDate

data class RegisterRequest (
    val username: String,
    val email: String,
    val address: String,
    val dateOfBirth: String?, // <-- Đổi từ LocalDate sang String
    val phone: String,
    val password: String,
    val school: String
)
