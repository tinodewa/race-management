package com.hit.racemanagement.ui.shared

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun RmsScaffold(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background, // Default warna tema
    content: @Composable (PaddingValues) -> Unit // Meneruskan padding ke anak
) {
    Scaffold(
        modifier = modifier,
        containerColor = backgroundColor, // Warna background dinamis per halaman
        contentWindowInsets = WindowInsets.safeDrawing // Otomatis handle safe area
    ) { innerPadding ->
        content(innerPadding)
    }
}