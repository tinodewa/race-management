package com.hit.racemanagement.ui.features.leaderboard.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
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

@Composable
fun RacerListItem(racer: RacerResultDummy, isCompact: Boolean) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rank Number
            Text(
                text = "${racer.rank}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = if (racer.rank <= 3) PrimaryRed else Color.Gray,
                modifier = Modifier.width(40.dp)
            )

            // Racer Info
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = racer.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // Badge Nomor Start
                    Surface(
                        color = SecondaryBlue,
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Text(
                            text = "#${racer.number}",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }

                // Tampilkan Team/Motor hanya jika Expanded
                if (!isCompact) {
                    Text(
                        text = "${racer.team} • ${racer.vehicle}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            // Team Column (Khusus Desktop)
            if (!isCompact) {
                Text(
                    text = racer.team,
                    modifier = Modifier.weight(1f),
                    color = Color.DarkGray,
                    fontSize = 14.sp
                )
            }

            // Timing Info
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = racer.time,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                if (racer.gap != "-") {
                    Text(
                        text = racer.gap,
                        fontSize = 12.sp,
                        color = PrimaryRed,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}