package com.hit.racemanagement.ui.features.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hit.racemanagement.ui.features.leaderboard.component.HeaderSection
import com.hit.racemanagement.ui.features.leaderboard.component.PodiumSection
import com.hit.racemanagement.ui.features.leaderboard.component.RacerListItem
import com.hit.racemanagement.ui.features.leaderboard.component.RacerResultDummy
import com.hit.racemanagement.ui.features.leaderboard.component.dummyResults
import com.hit.racemanagement.ui.theme.SecondaryBlue
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

@Composable
fun LeaderboardContent() {
    val navigator = LocalNavigator.currentOrThrow

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Abu-abu muda clean
    ) {
        if (maxWidth < 800.dp) {
            LeaderboardCompact(dummyResults, onBackClick = { navigator.pop() })
        } else {
            LeaderboardExpanded(dummyResults, onBackClick = { navigator.pop() })
        }
    }
}


// --- TAMPILAN MOBILE (Compact) ---
@Composable
fun LeaderboardCompact(results: List<RacerResultDummy>, onBackClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        // 1. Header & Filter Kecil
        HeaderSection(isCompact = true, { onBackClick() })

        // 2. Podium (Top 3) Horizontal Scroll atau Stacked
        // Di mobile, podium kadang memakan tempat, kita taruh Top 3 sebagai Card Spesial

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    "TOP RACERS",
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(results) { racer ->
                RacerListItem(racer, isCompact = true)
            }
        }
    }
}

// --- TAMPILAN DESKTOP / WEB (Expanded) ---
@Composable
fun LeaderboardExpanded(results: List<RacerResultDummy>, onBackClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxSize()) {
        // Kolom Kiri: Podium Visual (30%)
        Box(
            modifier = Modifier
                .weight(0.35f)
                .fillMaxHeight()
                .background(SecondaryBlue),
            contentAlignment = Alignment.Center
        ) {
            PodiumSection(results.take(3))
        }

        // Kolom Kanan: Tabel Detail (70%)
        Column(
            modifier = Modifier
                .weight(0.65f)
                .fillMaxHeight()
                .padding(32.dp)
        ) {
            HeaderSection(isCompact = false, { onBackClick() })

            Spacer(modifier = Modifier.height(24.dp))

            // Header Tabel
            Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text("RANK", modifier = Modifier.width(60.dp), fontWeight = FontWeight.Bold, color = Color.Gray)
                Text("RACER", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, color = Color.Gray)
                Text("TEAM", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, color = Color.Gray)
                Text("TIME", modifier = Modifier.width(100.dp), textAlign = TextAlign.End, fontWeight = FontWeight.Bold, color = Color.Gray)
                Text("GAP", modifier = Modifier.width(80.dp), textAlign = TextAlign.End, fontWeight = FontWeight.Bold, color = Color.Gray)
            }

            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

            LazyColumn(
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(results) { racer ->
                    RacerListItem(racer, isCompact = false)
                }
            }
        }
    }
}