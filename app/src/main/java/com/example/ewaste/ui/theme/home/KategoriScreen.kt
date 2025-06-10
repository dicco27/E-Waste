package com.example.ewaste.ui.theme.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
fun KategoriScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kategori Sampah", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(top = 32.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            KategoriItem(label = "Jenis sampah kecil") {
                navController.navigate("sampah-kecil")
            }

            KategoriItem(label = "Jenis sampah besar") {
                navController.navigate("sampah-besar")
            }
        }
    }
}

@Composable
fun KategoriItem(label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 14.sp, color = Color.Black)

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier
                .size(100.dp)
                .clickable { onClick() },
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color(0xFF2E7D32)),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.sampah),
                    contentDescription = label,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}