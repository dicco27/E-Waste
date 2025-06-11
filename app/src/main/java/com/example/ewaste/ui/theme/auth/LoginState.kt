package com.example.ewaste.ui.theme.auth

import com.example.ewaste.data.remote.dto.LoginResponse

sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data class Success(val data: LoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}