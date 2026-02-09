package com.hit.racemanagement.ui.features.auth.register

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class RegisterScreen: Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        RegisterContent()
    }
}