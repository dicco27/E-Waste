package com.example.ewaste.ui.theme.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ewaste.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SampahKecilScreen(navController: NavController) {
    val items = listOf(
        "Smartphone", "PC", "Kamera",
        "Kalkulator", "Radio", "Laptop"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Jenis Sampah Kecil", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = padding,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            items(items.size) { index ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.sampah),
                        contentDescription = items[index],
                        modifier = Modifier
                            .size(80.dp)
                            .border(1.dp, Color(0xFF2E7D32), RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = items[index], fontSize = 14.sp)
                }
            }
        }
    }
}