package com.example.ewaste.ui.theme.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ResetSuccessScreen(navController: NavController) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Berhasil", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Silahkan cek email kamu")

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("login") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Text("Masuk", color = Color.White)
            }
        }
    }
}