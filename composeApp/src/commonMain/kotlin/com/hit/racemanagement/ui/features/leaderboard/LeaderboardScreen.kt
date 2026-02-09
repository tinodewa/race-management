package com.hit.racemanagement.ui.features.leaderboard

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class LeaderboardScreen: Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        LeaderboardContent()
    }
}
