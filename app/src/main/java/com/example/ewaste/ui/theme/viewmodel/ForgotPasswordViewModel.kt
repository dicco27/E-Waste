package com.example.ewaste.ui.theme.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ewaste.data.remote.dto.ForgotPasswordRequest
import com.example.ewaste.data.remote.dto.ResetPasswordRequest
import com.example.ewaste.data.repository.UserRepository
import kotlinx.coroutines.launch

sealed class RequestState {
    data object Idle : RequestState()
    data object Loading : RequestState()
    data class Success(val message: String) : RequestState()
    data class Error(val message: String) : RequestState()
}

class ForgotPasswordViewModel(private val userRepository: UserRepository) : ViewModel() {

    // Menyimpan email & OTP untuk digunakan di beberapa layar
    var email = mutableStateOf("")
    var otp = mutableStateOf("")

    var requestOtpState = mutableStateOf<RequestState>(RequestState.Idle)
        private set

    var resetPasswordState = mutableStateOf<RequestState>(RequestState.Idle)
        private set

    fun requestOtp(emailInput: String) {
        viewModelScope.launch {
            requestOtpState.value = RequestState.Loading
            email.value = emailInput // Simpan email untuk langkah berikutnya
            userRepository.forgotPassword(ForgotPasswordRequest(emailInput)).fold(
                onSuccess = { response ->
                    requestOtpState.value = RequestState.Success(response.message)
                },
                onFailure = {
                    requestOtpState.value = RequestState.Error(it.message ?: "Unknown error")
                }
            )
        }
    }

    fun resetPassword(password: String, passwordConfirmation: String) {
        viewModelScope.launch {
            resetPasswordState.value = RequestState.Loading
            val request = ResetPasswordRequest(
                email = email.value,
                token = otp.value,
                password = password,
                passwordConfirmation = passwordConfirmation
            )
            userRepository.resetPassword(request).fold(
                onSuccess = { response ->
                    resetPasswordState.value = RequestState.Success(response.message)
                },
                onFailure = {
                    resetPasswordState.value = RequestState.Error(it.message ?: "Unknown error")
                }
            )
        }
    }

    fun resetAllStates() {
        requestOtpState.value = RequestState.Idle
        resetPasswordState.value = RequestState.Idle
    }
}