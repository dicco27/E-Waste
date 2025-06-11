package com.example.ewaste.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ewaste.data.local.SessionManager
import com.example.ewaste.data.repository.UserRepository
import com.example.ewaste.di.NetworkModule
import com.example.ewaste.ui.theme.auth.*
import com.example.ewaste.ui.theme.home.*
import com.example.ewaste.ui.theme.profile.ChangePasswordScreen
import com.example.ewaste.ui.theme.profile.ProfileScreen
import com.example.ewaste.ui.theme.viewmodel.*
import com.example.ewaste.ui.theme.welcome.WelcomeScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    // Menggunakan `remember` untuk membuat instance dependensi hanya sekali
    val context = LocalContext.current
    val viewModelFactory = remember {
        val sessionManager = SessionManager(context)
        val apiService = NetworkModule.provideApiService(sessionManager)
        val userRepository = UserRepository(apiService)
        ViewModelFactory(userRepository, sessionManager)
    }

    // ViewModel yang dibagikan untuk alur lupa password
    val forgotPasswordViewModel: ForgotPasswordViewModel = viewModel(factory = viewModelFactory)

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }

        composable("login") {
            LoginScreen(
                navController = navController,
                viewModel = viewModel(modelClass = LoginViewModel::class.java, factory = viewModelFactory)
            )
        }

        composable("register") {
            RegisterScreen(
                navController = navController,
                viewModel = viewModel(modelClass = RegisterViewModel::class.java, factory = viewModelFactory)
            )
        }

        composable("home") {
            HomeScreen(
                navController = navController,
                viewModel = viewModel(modelClass = HomeViewModel::class.java, factory = viewModelFactory)
            )
        }

        composable("profile") {
            ProfileScreen(
                navController = navController,
                viewModel = viewModel(modelClass = HomeViewModel::class.java, factory = viewModelFactory)
            )
        }

        composable("ubah-sandi") {
            ChangePasswordScreen(
                navController = navController,
                viewModel = viewModel(modelClass = ChangePasswordViewModel::class.java, factory = viewModelFactory)
            )
        }

        // --- TAMBAHAN RUTE YANG HILANG ---
        composable("kategori") {
            KategoriScreen(navController = navController)
        }
        composable("sampah-kecil") {
            SampahKecilScreen(navController = navController)
        }
        composable("sampah-besar") {
            SampahBesarScreen(navController = navController)
        }
        // ------------------------------------

        // --- RUTE UNTUK ALUR LUPA PASSWORD ---
        composable("forgot") {
            ForgotPasswordScreen(
                navController = navController,
                viewModel = forgotPasswordViewModel
            )
        }
        composable("otp") {
            OtpScreen(
                navController = navController,
                viewModel = forgotPasswordViewModel
            )
        }
        composable("reset-password") {
            ResetPasswordScreen(
                navController = navController,
                viewModel = forgotPasswordViewModel
            )
        }
        composable("reset-success") {
            ResetSuccessScreen(navController)
        }
        // ------------------------------------
    }
}