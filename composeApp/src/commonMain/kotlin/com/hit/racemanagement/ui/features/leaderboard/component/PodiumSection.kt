package com.hit.racemanagement.ui.features.leaderboard.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hit.racemanagement.ui.theme.BronzeColor
import com.hit.racemanagement.ui.theme.GoldColor
import com.hit.racemanagement.ui.theme.SilverColor

@Composable
fun PodiumSection(top3: List<RacerResultDummy>) {
    // Visualisasi Podium Sederhana
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(16.dp)
    ) {
        // Juara 2 (Kiri)
        if (top3.size > 1) PodiumBar(top3[1], height = 150, color = SilverColor)

        Spacer(modifier = Modifier.width(8.dp))

        // Juara 1 (Tengah - Paling Tinggi)
        if (top3.isNotEmpty()) PodiumBar(top3[0], height = 200, color = GoldColor)

        Spacer(modifier = Modifier.width(8.dp))

        // Juara 3 (Kanan)
        if (top3.size > 2) PodiumBar(top3[2], height = 120, color = BronzeColor)
    }
}
