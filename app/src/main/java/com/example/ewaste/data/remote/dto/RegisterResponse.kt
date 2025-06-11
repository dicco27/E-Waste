package com.example.ewaste.data.remote.dto

data class RegisterResponse(
    val user: User,
    val token: String
)

data class User(
    val id: Int,
    val name: String,
    val email: String,
    // field lain sesuai API response
)
