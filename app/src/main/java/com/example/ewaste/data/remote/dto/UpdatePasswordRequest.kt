package com.example.ewaste.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UpdatePasswordRequest(
    @SerializedName("current_password")
    val currentPassword: String,

    @SerializedName("password")
    val newPassword: String,

    @SerializedName("password_confirmation")
    val newPasswordConfirmation: String
)