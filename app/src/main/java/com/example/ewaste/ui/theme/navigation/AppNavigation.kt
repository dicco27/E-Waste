package com.example.ewaste.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ewaste.ui.theme.auth.LoginScreen
import com.example.ewaste.ui.theme.auth.RegisterScreen
import com.example.ewaste.ui.theme.home.HomeScreen
import com.example.ewaste.ui.theme.welcome.WelcomeScreen
import com.example.ewaste.ui.theme.home.KategoriScreen
import com.example.ewaste.ui.theme.profile.ProfileScreen
import com.example.ewaste.ui.theme.profile.ChangePasswordScreen
import com.example.ewaste.ui.theme.auth.ForgotPasswordScreen
import com.example.ewaste.ui.theme.auth.OtpScreen
import com.example.ewaste.ui.theme.auth.ResetSuccessScreen
import com.example.ewaste.ui.theme.auth.ResetPasswordScreen
import com.example.ewaste.ui.theme.home.SampahBesarScreen
import com.example.ewaste.ui.theme.home.SampahKecilScreen



@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("kategori") { KategoriScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("ubah-sandi") { ChangePasswordScreen(navController) }
        composable("forgot") { ForgotPasswordScreen(navController) }
        composable("otp") { OtpScreen(navController) }
        composable("reset-password") { ResetPasswordScreen(navController) }
        composable("reset-success") { ResetSuccessScreen(navController) }
        composable("sampah-besar") { SampahBesarScreen(navController) }
        composable("sampah-kecil") { SampahKecilScreen(navController) }
        composable("profile") { ProfileScreen(navController) }


    }
}