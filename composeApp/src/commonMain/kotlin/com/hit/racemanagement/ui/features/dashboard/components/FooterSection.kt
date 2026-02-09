package com.hit.racemanagement.ui.features.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hit.racemanagement.ui.theme.SecondaryBlue

@Composable
fun FooterSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(SecondaryBlue),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "RACE MANAGEMENT SYSTEM © 2026",
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 12.sp
        )
    }
}