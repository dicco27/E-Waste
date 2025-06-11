package com.example.ewaste.ui.theme.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ewaste.data.remote.dto.RegisterRequest
import com.example.ewaste.data.remote.dto.RegisterResponse
import com.example.ewaste.data.repository.UserRepository
import kotlinx.coroutines.launch

sealed class RegisterState {
    data object Idle : RegisterState()
    data object Loading : RegisterState()
    data class Success(val data: RegisterResponse) : RegisterState()
    data class Error(val message: String) : RegisterState()
}

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    var registerState = mutableStateOf<RegisterState>(RegisterState.Idle)
        private set

    fun register(name: String, email: String, password: String, noHp: String?, alamat: String?) {
        viewModelScope.launch {
            registerState.value = RegisterState.Loading
            val request = RegisterRequest(
                name = name,
                email = email,
                password = password,
                no_hp = noHp,
                alamat = alamat
            )
            val result = userRepository.registerUser(request)

            result.fold(
                onSuccess = { response ->
                    registerState.value = RegisterState.Success(response)
                },
                onFailure = { exception ->
                    registerState.value = RegisterState.Error(exception.message ?: "Unknown error")
                }
            )
        }
    }
}