package com.example.ewaste.ui.theme.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ewaste.ui.theme.viewmodel.RegisterState
import com.example.ewaste.ui.theme.viewmodel.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel
) {
    val state by viewModel.registerState

    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // --- TAMBAHKAN SCAFFOLD DENGAN TOPAPPBAR ---
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daftar Akun") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent // Agar menyatu dengan background
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Gunakan padding dari Scaffold
                .padding(horizontal = 24.dp), // Padding tambahan untuk konten
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = nama,
                onValueChange = { nama = it },
                label = { Text("Nama") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.register(nama, email, password, null, null)
                },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                enabled = state !is RegisterState.Loading
            ) {
                Text("Daftar")
            }

            Spacer(modifier = Modifier.height(16.dp))
            when (val currentState = state) {
                is RegisterState.Loading -> CircularProgressIndicator()
                is RegisterState.Success -> {
                    // Tampilkan pesan atau langsung navigasi
                    LaunchedEffect(Unit) {
                        // Idealnya, tampilkan pesan sukses dulu sebelum navigasi
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
                        }
                    }
                }
                is RegisterState.Error -> {
                    Text(text = currentState.message, color = MaterialTheme.colorScheme.error)
                }
                else -> {}
            }
        }
    }
}