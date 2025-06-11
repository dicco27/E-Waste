package com.example.ewaste.data.remote.Service

import com.example.ewaste.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    // ... (Fungsi yang sudah ada)

    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("profile")
    suspend fun getProfile(): Response<ProfileResponse>

    @POST("profile/update")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): Response<UpdateProfileResponse>

    @POST("password/update")
    suspend fun updatePassword(@Body request: UpdatePasswordRequest): Response<MessageResponse> // Ubah ke MessageResponse

    // --- TAMBAHKAN ENDPOINT BARU INI ---
    @POST("password/forgot")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<MessageResponse>

    @POST("password/reset")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<MessageResponse>


}