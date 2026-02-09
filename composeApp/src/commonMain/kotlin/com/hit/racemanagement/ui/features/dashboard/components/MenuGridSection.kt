package com.hit.racemanagement.ui.features.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuGridSection(modifier: Modifier = Modifier, columns: Int, useDarkTheme: Boolean = false) {
    val menuItems = listOf(
        MenuItem("Leaderboard", Icons.Default.Leaderboard),
        MenuItem("My Profile", Icons.Default.Person),
        MenuItem("Scan QR", Icons.Default.QrCodeScanner), // Marshal Only?
        MenuItem("Rules", Icons.Default.Flag),
        MenuItem("Schedule", Icons.Default.Timer),
        MenuItem("Settings", Icons.Default.Settings),
    )

    // Kita pakai Column + Loop manual atau FlowRow jika LazyGrid bermasalah di dalam Scrollable Column
    // Untuk safety, di sini saya pakai Column biasa dengan Row (simple grid)
    // karena LazyVerticalGrid di dalam Column scrollable kadang crash di UI logic sederhana.

    Column(modifier = modifier) {
        menuItems.chunked(columns).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { item ->
                    DashboardCard(
                        item = item,
                        modifier = Modifier.weight(1f),
                        isDarkBg = useDarkTheme
                    )
                }
                // Isi kekosongan jika ganjil
                if (rowItems.size < columns) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}