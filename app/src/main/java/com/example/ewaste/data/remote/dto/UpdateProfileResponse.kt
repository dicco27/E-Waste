package com.example.ewaste.data.remote.dto

data class UpdateProfileResponse(
    val message: String,
    val user: ProfileResponse // Menggunakan ulang ProfileResponse yang sudah ada
)