package com.hit.racemanagement.ui.features.dashboard.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hit.racemanagement.ui.theme.PrimaryRed
import com.hit.racemanagement.ui.theme.SecondaryBlue

@Composable
fun HeroSection(modifier: Modifier = Modifier, titleSize: Int, taglineSize: Int) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        // Logo Placeholder (Icon Gunung/Trail)
        Icon(
            imageVector = Icons.Default.Flag,
            contentDescription = "Logo",
            tint = PrimaryRed,
            modifier = Modifier.size(64.dp).padding(bottom = 16.dp)
        )

        Text(
            text = "DIRT CHALLENGE",
            fontSize = titleSize.sp,
            fontWeight = FontWeight.Black,
            color = SecondaryBlue,
            textAlign = TextAlign.Center,
            lineHeight = (titleSize * 1.1).sp
        )

        Text(
            text = "#BERANIKOTOR", // Tagline ala #SudahBasah
            fontSize = taglineSize.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryRed,
            modifier = Modifier.padding(top = 8.dp),
            letterSpacing = 2.sp
        )

        Text(
            text = "10km • 25km • Time Attack",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
