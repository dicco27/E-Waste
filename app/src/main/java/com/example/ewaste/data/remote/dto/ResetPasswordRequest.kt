package com.example.ewaste.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    val email: String,
    val token: String, // Ini adalah OTP
    val password: String,
    @SerializedName("password_confirmation")
    val passwordConfirmation: String
)