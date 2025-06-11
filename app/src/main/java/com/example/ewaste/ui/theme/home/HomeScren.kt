package com.example.ewaste.ui.theme.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ewaste.R
import com.example.ewaste.ui.theme.viewmodel.HomeState
import com.example.ewaste.ui.theme.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val state = viewModel.homeState.value

    // --- Definisi warna untuk NavigationBarItem ---
    val navItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.White,
        unselectedIconColor = Color.White.copy(alpha = 0.7f),
        selectedTextColor = Color.White,
        unselectedTextColor = Color.White.copy(alpha = 0.7f),
        indicatorColor = Color.White.copy(alpha = 0.2f) // <-- Anda bisa atur ini
        // Jika ingin benar-benar hilang, gunakan: indicatorColor = Color.Transparent
    )
    // ---------------------------------------------

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF2E7D32)) {
                NavigationBarItem(
                    selected = currentRoute == "profile",
                    onClick = { navController.navigate("profile") },
                    icon = { Icon(Icons.Default.Person, "Profil", tint = Color.White) },
                    label = { Text("Profil", color = Color.White) },
                    colors = navItemColors // Terapkan warna
                )
                NavigationBarItem(
                    selected = currentRoute == "home",
                    onClick = { /* Sudah di sini */ },
                    icon = { Icon(Icons.Default.Home, "Beranda", tint = Color.White) },
                    label = { Text("Beranda", color = Color.White) },
                    colors = navItemColors // Terapkan warna
                )
                NavigationBarItem(
                    selected = currentRoute == "kategori",
                    onClick = { navController.navigate("kategori") },
                    icon = { Icon(Icons.Default.Category, "Kategori", tint = Color.White) },
                    label = { Text("Kategori", color = Color.White) },
                    colors = navItemColors // Terapkan warna
                )
            }
        }
    ) { innerPadding ->
        // ... (when (state) { ... } dan sisa kode Anda tetap sama) ...
        when (state) {
            is HomeState.Loading -> {
                Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is HomeState.Error -> {
                Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                    Text(text = "Gagal memuat data: ${state.message}", color = Color.Red, modifier = Modifier.padding(16.dp))
                }
            }
            is HomeState.Success -> {
                val profile = state.profile
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth().background(Color(0xFF2E7D32)).padding(16.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Search, "Cari", tint = Color.White)
                                Text(
                                    text = "Halo, ${profile.name.split(" ").firstOrNull() ?: profile.name}",
                                    color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold
                                )
                                Icon(Icons.Default.Notifications, "Notifikasi", tint = Color.White)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.profile),
                                        contentDescription = "Foto Profil",
                                        modifier = Modifier.size(40.dp).clip(CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(profile.name, fontWeight = FontWeight.Bold)
                                        Text(profile.email)
                                    }
                                    TextButton(onClick = { navController.navigate("profile") }) {
                                        Text("Lihat Profil", color = Color(0xFF2E7D32))
                                    }
                                }
                            }
                        }
                    }
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Rp 7.000.000", fontWeight = FontWeight.Bold)
                                Text("Bayar  |  Top Up  |  Lainnya", color = Color(0xFF2E7D32))
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("150 poin", color = Color.DarkGray)
                        }
                    }
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        val fiturList = listOf("Lokasi", "Jemput", "Perawatan", "Voucher", "CS", "Lihat Semua")
                        val icons = listOf(
                            Icons.Default.Place, Icons.Default.LocalShipping, Icons.Default.Build,
                            Icons.Default.Money, Icons.Default.Headphones, Icons.Default.Menu
                        )
                        fiturList.chunked(3).forEachIndexed { _, rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                rowItems.forEach { fitur ->
                                    val icon = icons[fiturList.indexOf(fitur)]
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Card(
                                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                                            shape = CircleShape,
                                            modifier = Modifier.size(64.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                                Icon(imageVector = icon, contentDescription = fitur, tint = Color(0xFF2E7D32))
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(fitur, fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Berita Lingkungan", modifier = Modifier.padding(horizontal = 16.dp), fontWeight = FontWeight.Bold)
                    LazyRow(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
                        items(3) {
                            Card(
                                modifier = Modifier.width(240.dp).padding(8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                            ) {
                                Column {
                                    Image(
                                        painter = painterResource(id = R.drawable.pandawara),
                                        contentDescription = "Berita Pandawara",
                                        modifier = Modifier.fillMaxWidth().height(120.dp)
                                    )
                                    Text(
                                        "Pandawara Group: Aksi Anak Muda Berhasil...",
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}