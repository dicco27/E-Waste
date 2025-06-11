package com.example.ewaste.ui.theme.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ewaste.data.local.SessionManager
import com.example.ewaste.data.remote.dto.ProfileResponse
import com.example.ewaste.data.remote.dto.UpdateProfileRequest
import com.example.ewaste.data.repository.UserRepository
import kotlinx.coroutines.launch

// State untuk proses update profil
sealed class UpdateProfileState {
    data object Idle : UpdateProfileState()
    data object Loading : UpdateProfileState()
    data class Success(val message: String) : UpdateProfileState()
    data class Error(val message: String) : UpdateProfileState()
}

// State untuk memuat data profil utama
sealed class HomeState {
    data object Loading : HomeState()
    data class Success(val profile: ProfileResponse) : HomeState()
    data class Error(val message: String) : HomeState()
}

class HomeViewModel(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager // <-- PERUBAHAN: Tambahkan SessionManager
) : ViewModel() {

    var homeState = mutableStateOf<HomeState>(HomeState.Loading)
        private set

    var updateProfileState = mutableStateOf<UpdateProfileState>(UpdateProfileState.Idle)
        private set

    init {
        fetchUserProfile()
    }

    fun fetchUserProfile() {
        viewModelScope.launch {
            homeState.value = HomeState.Loading
            userRepository.getProfile().fold(
                onSuccess = { response ->
                    homeState.value = HomeState.Success(response)
                },
                onFailure = { exception ->
                    homeState.value = HomeState.Error(exception.message ?: "Failed to get profile")
                }
            )
        }
    }

    fun updateProfile(name: String, noHp: String?, alamat: String?) {
        viewModelScope.launch {
            updateProfileState.value = UpdateProfileState.Loading
            val request = UpdateProfileRequest(
                name = name,
                no_hp = noHp?.takeIf { it.isNotBlank() },
                alamat = alamat?.takeIf { it.isNotBlank() }
            )

            userRepository.updateProfile(request).fold(
                onSuccess = { response ->
                    fetchUserProfile()
                    updateProfileState.value = UpdateProfileState.Success(response.message)
                },
                onFailure = { exception ->
                    updateProfileState.value = UpdateProfileState.Error(exception.message ?: "Update profile failed")
                }
            )
        }
    }

    fun resetUpdateState() {
        updateProfileState.value = UpdateProfileState.Idle
    }

    // --- FUNGSI BARU UNTUK LOGOUT ---
    fun logout() {
        viewModelScope.launch {
            sessionManager.clearAuthToken()
            // Jika Anda punya endpoint API untuk logout, panggil di sini
            // contoh: userRepository.logoutUser()
        }
    }
}