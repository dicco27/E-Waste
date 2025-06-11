package com.example.ewaste.ui.theme.profile

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack // Menggunakan versi AutoMirrored
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ewaste.ui.theme.viewmodel.ChangePasswordViewModel
import com.example.ewaste.ui.theme.viewmodel.UpdatePasswordState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    navController: NavController,
    viewModel: ChangePasswordViewModel
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var showCurrent by remember { mutableStateOf(false) }
    var showNew by remember { mutableStateOf(false) }
    var showConfirm by remember { mutableStateOf(false) }

    val updateState by viewModel.updatePasswordState
    val context = LocalContext.current

    LaunchedEffect(updateState) {
        when(val state = updateState) {
            is UpdatePasswordState.Success -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                viewModel.resetState()
                navController.popBackStack()
            }
            is UpdatePasswordState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ubah Kata Sandi", color = Color(0xFF2E7D32)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        // PERBAIKAN: Menggunakan ikon AutoMirrored untuk dukungan RTL
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PasswordField(
                label = "Kata Sandi Saat Ini",
                value = currentPassword,
                onValueChange = { currentPassword = it },
                isVisible = showCurrent,
                onToggleVisibility = { showCurrent = !showCurrent }
            )

            PasswordField(
                label = "Kata Sandi Baru",
                value = newPassword,
                onValueChange = { newPassword = it },
                isVisible = showNew,
                onToggleVisibility = { showNew = !showNew }
            )

            PasswordField(
                label = "Konfirmasi",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                isVisible = showConfirm,
                onToggleVisibility = { showConfirm = !showConfirm }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.updatePassword(currentPassword, newPassword, confirmPassword)
                },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                enabled = updateState !is UpdatePasswordState.Loading,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                if (updateState is UpdatePasswordState.Loading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Ubah Kata Sandi", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}

// --- PERBAIKAN: TAMBAHKAN DEFINISI COMPOSABLE INI ---
@Composable
fun PasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isVisible: Boolean,
    onToggleVisibility: () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = onToggleVisibility) {
                Icon(
                    imageVector = if (isVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = "Toggle Password Visibility"
                )
            }
        },
        singleLine = true
    )
}