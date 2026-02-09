package com.hit.racemanagement

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.hit.racemanagement.ui.features.dashboard.DashboardScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        // Start at ProductManagementScreen
        Navigator(DashboardScreen()) { navigator ->
            // Add default animation (Slide)
            SlideTransition(navigator)
        }
    }
}