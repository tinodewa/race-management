package com.hit.racemanagement.ui.features.leaderboard.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hit.racemanagement.ui.theme.PrimaryRed
import com.hit.racemanagement.ui.theme.SecondaryBlue
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@Composable
fun HeaderSection(isCompact: Boolean, onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(if (isCompact) 16.dp else 0.dp),
        verticalAlignment = Alignment.CenterVertically // Pastikan vertikal rata tengah
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // --- TOMBOL BACK ---
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.padding(end = 8.dp) // Jarak antara tombol dan icon timer
            ) {
                Icon(
                    // Gunakan AutoMirrored agar arah panah benar di mode RTL (Arab/Hebrew) jika support
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = PrimaryRed,
                    modifier = Modifier.size(if (isCompact) 24.dp else 32.dp)
                )
            }

            // --- JUDUL ASLI ---
            Icon(
                Icons.Default.Timer,
                contentDescription = null,
                tint = PrimaryRed,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "LIVE LEADERBOARD",
                    fontSize = if (isCompact) 20.sp else 32.sp,
                    fontWeight = FontWeight.Black,
                    color = if (isCompact) SecondaryBlue else Color.Black
                )
                Text(
                    text = "CLASS: TRAIL 150CC OPEN",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryRed,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}
