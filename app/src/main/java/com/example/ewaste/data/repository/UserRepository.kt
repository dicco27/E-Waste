package com.example.ewaste.data.repository

import com.example.ewaste.data.remote.Service.ApiService
import com.example.ewaste.data.remote.dto.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import kotlin.Result

/**
 * Repository yang menangani semua operasi terkait data pengguna,
 * baik dari network (API) maupun dari database lokal.
 */
class UserRepository(private val apiService: ApiService) {

    suspend fun registerUser(request: RegisterRequest): Result<RegisterResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.register(request)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = errorBody?.let { JSONObject(it).getString("message") } ?: "Register failed"
                    Result.failure(Exception(message))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun loginUser(request: LoginRequest): Result<LoginResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.login(request)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = errorBody?.let { JSONObject(it).getString("message") } ?: "Login failed"
                    Result.failure(Exception(message))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getProfile(): Result<ProfileResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getProfile()
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Get profile failed: ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun updateProfile(request: UpdateProfileRequest): Result<UpdateProfileResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.updateProfile(request)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Update profile failed: ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun updatePassword(request: UpdatePasswordRequest): Result<MessageResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.updatePassword(request)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = errorBody?.let { JSONObject(it).getString("message") } ?: "Update password failed"
                    Result.failure(Exception(message))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // --- FUNGSI BARU UNTUK LUPA PASSWORD ---
    suspend fun forgotPassword(request: ForgotPasswordRequest): Result<MessageResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.forgotPassword(request)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = errorBody?.let { JSONObject(it).getString("message") } ?: "Gagal mengirim OTP"
                    Result.failure(Exception(message))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun resetPassword(request: ResetPasswordRequest): Result<MessageResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.resetPassword(request)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = errorBody?.let { JSONObject(it).getString("message") } ?: "Gagal mereset password"
                    Result.failure(Exception(message))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}