package com.example.ewaste.ui.theme.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(navController: NavController) {
    var otp by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
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
            Text("Silahkan cek email kamu.", fontWeight = FontWeight.Bold)
            Text("Kami telah mengirimkan kode ke email anggans@gmail.com")

            // Sederhana, hanya satu field untuk OTP
            OutlinedTextField(
                value = otp,
                onValueChange = { otp = it },
                label = { Text("Kode OTP") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { navController.navigate("reset-password") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Text("Kirim", color = Color.White)
            }
        }
    }
}