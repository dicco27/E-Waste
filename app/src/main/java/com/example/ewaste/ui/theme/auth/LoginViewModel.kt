package com.example.ewaste.ui.theme.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ewaste.data.local.SessionManager
import com.example.ewaste.data.remote.dto.LoginRequest
import com.example.ewaste.data.repository.UserRepository
import kotlinx.coroutines.launch

// Jangan definisikan ulang LoginState di sini

class LoginViewModel(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    var loginState = mutableStateOf<LoginState>(LoginState.Idle)
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginState.value = LoginState.Loading
            val request = LoginRequest(email = email, password = password)

            userRepository.loginUser(request).fold(
                onSuccess = { response ->
                    sessionManager.saveAuthToken(response.token)
                    loginState.value = LoginState.Success(response)
                },
                onFailure = { exception ->
                    loginState.value = LoginState.Error(exception.message ?: "Unknown error")
                }
            )
        }
    }
}