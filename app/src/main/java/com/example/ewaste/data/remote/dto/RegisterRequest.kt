package com.example.ewaste.data.remote.dto

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val no_hp: String? = null,
    val alamat: String? = null,
)
