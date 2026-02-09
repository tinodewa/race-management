package com.hit.racemanagement.ui.features.leaderboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hit.racemanagement.ui.theme.SecondaryBlue

@Composable
fun PodiumBar(racer: RacerResultDummy, height: Int, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Avatar / Initials
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "#${racer.number}",
                fontWeight = FontWeight.Bold,
                color = SecondaryBlue
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        // Batang Podium
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(height.dp)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .background(color),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = "${racer.rank}",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = racer.name.split(" ").first(), // Nama Depan Saja
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}