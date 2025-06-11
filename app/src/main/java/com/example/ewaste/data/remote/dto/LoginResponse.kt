package com.example.ewaste.data.remote.dto

// Anda bisa menggunakan ulang RegisterResponse jika strukturnya sama persis,
// atau buat yang baru seperti ini untuk membedakannya.
data class LoginResponse(
    val user: User, // Menggunakan ulang data class User dari RegisterResponse
    val token: String
)