package com.example.ewaste.data.remote.dto

// DTO ini hanya merepresentasikan data user yang diterima dari /api/profile
data class ProfileResponse(
    val id: Int,
    val name: String,
    val email: String,
    val no_hp: String?,
    val alamat: String?
)