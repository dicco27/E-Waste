package com.example.ewaste.ui.theme.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ewaste.data.remote.dto.UpdatePasswordRequest
import com.example.ewaste.data.repository.UserRepository
import kotlinx.coroutines.launch

sealed class UpdatePasswordState {
    data object Idle : UpdatePasswordState()
    data object Loading : UpdatePasswordState()
    data class Success(val message: String) : UpdatePasswordState()
    data class Error(val message: String) : UpdatePasswordState()
}

class ChangePasswordViewModel(private val userRepository: UserRepository) : ViewModel() {

    var updatePasswordState = mutableStateOf<UpdatePasswordState>(UpdatePasswordState.Idle)
        private set

    fun updatePassword(currentPass: String, newPass: String, confirmPass: String) {
        if (newPass.length < 8) {
            updatePasswordState.value = UpdatePasswordState.Error("Password baru minimal 8 karakter.")
            return
        }
        if (newPass != confirmPass) {
            updatePasswordState.value = UpdatePasswordState.Error("Password baru tidak cocok.")
            return
        }

        viewModelScope.launch {
            updatePasswordState.value = UpdatePasswordState.Loading
            val request = UpdatePasswordRequest(
                currentPassword = currentPass,
                newPassword = newPass,
                newPasswordConfirmation = confirmPass
            )
            userRepository.updatePassword(request).fold(
                onSuccess = {
                    updatePasswordState.value = UpdatePasswordState.Success("Password berhasil diubah!")
                },
                onFailure = { exception ->
                    updatePasswordState.value = UpdatePasswordState.Error(exception.message ?: "Gagal mengubah password")
                }
            )
        }
    }

    fun resetState() {
        updatePasswordState.value = UpdatePasswordState.Idle
    }
}