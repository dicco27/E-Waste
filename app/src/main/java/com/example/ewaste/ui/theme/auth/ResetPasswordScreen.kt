package com.example.ewaste.ui.theme.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ewaste.ui.theme.viewmodel.ForgotPasswordViewModel
import com.example.ewaste.ui.theme.viewmodel.RequestState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel // <-- PASTIKAN PARAMETER INI ADA
) {
    var newPass by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }
    var show1 by remember { mutableStateOf(false) }
    var show2 by remember { mutableStateOf(false) }

    val state by viewModel.resetPasswordState
    val context = LocalContext.current

    LaunchedEffect(state) {
        when(val currentState = state) {
            is RequestState.Success -> {
                Toast.makeText(context, currentState.message, Toast.LENGTH_SHORT).show()
                navController.navigate("reset-success") {
                    popUpTo("forgot") { inclusive = true }
                }
                viewModel.resetAllStates()
            }
            is RequestState.Error -> {
                Toast.makeText(context, currentState.message, Toast.LENGTH_LONG).show()
                viewModel.resetAllStates()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reset Password", color = Color(0xFF2E7D32)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = newPass,
                onValueChange = { newPass = it },
                label = { Text("Password Baru") },
                visualTransformation = if (show1) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { show1 = !show1 }) {
                        Icon(if (show1) Icons.Default.VisibilityOff else Icons.Default.Visibility, null)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = confirmPass,
                onValueChange = { confirmPass = it },
                label = { Text("Konfirmasi") },
                visualTransformation = if (show2) VisualTransformation.None else PasswordVisualTransformation(),
                isError = state is RequestState.Error,
                trailingIcon = {
                    IconButton(onClick = { show2 = !show2 }) {
                        Icon(if (show2) Icons.Default.VisibilityOff else Icons.Default.Visibility, null)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (state is RequestState.Error) {
                Text(
                    text = (state as RequestState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = { viewModel.resetPassword(newPass, confirmPass) },
                modifier = Modifier.fillMaxWidth(),
                enabled = state !is RequestState.Loading,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                if (state is RequestState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                } else {
                    Text("Reset Password", color = Color.White)
                }
            }
        }
    }
}