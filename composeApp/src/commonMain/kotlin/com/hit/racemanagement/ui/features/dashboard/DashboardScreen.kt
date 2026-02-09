package com.hit.racemanagement.ui.features.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- Colors Definition (Based on Mantra/Lelono Vibe) ---
private val MantraRed = Color(0xFFC62828)
private val MantraBlue = Color(0xFF0D47A1)
private val PaperWhite = Color(0xFFFAFAFA)
private val DarkText = Color(0xFF1A1A1A)

@Composable
fun DashboardScreen() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(PaperWhite)
    ) {
        // Tentukan breakpoint sederhana (600dp biasanya batas HP/Tablet)
        if (maxWidth < 800.dp) {
            DashboardCompact(maxWidth)
        } else {
            DashboardExpanded(maxWidth)
        }
    }
}

// --- Layout untuk HP (Vertical) ---
@Composable
fun DashboardCompact(width: Dp) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(width)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Hero Section (Header)
        HeroSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 32.dp, start = 16.dp, end = 16.dp),
            titleSize = 42,
            taglineSize = 18
        )

        // 2. Action Button Utama (Seperti tombol 'Results' di referensi)
        PrimaryActionButton(
            text = "LIVE RACE STATUS",
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // 3. Grid Menu
        MenuGridSection(
            modifier = Modifier.padding(horizontal = 16.dp),
            columns = 2 // 2 Kolom di HP
        )

        Spacer(modifier = Modifier.height(50.dp))

        // Footer dekoratif
        FooterSection()
    }
}

// --- Layout untuk Desktop/Web (Horizontal Split) ---
@Composable
fun DashboardExpanded(width: Dp) {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        // Kiri: Branding Area (50% layar)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Brush.verticalGradient(listOf(PaperWhite, Color(0xFFEFEFEF)))),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                HeroSection(
                    modifier = Modifier.padding(32.dp),
                    titleSize = 64, // Lebih besar di desktop
                    taglineSize = 24
                )
                PrimaryActionButton(
                    text = "LIVE RACE STATUS",
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
        }

        // Kanan: Functional Grid (50% layar)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(MantraBlue) // Kontras biru tua di sisi kanan
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
            MenuGridSection(
                modifier = Modifier.width(width),
                columns = 2,
                useDarkTheme = true // Menu jadi terang di atas background gelap
            )
        }
    }
}

// --- Komponen-komponen UI ---

@Composable
fun HeroSection(modifier: Modifier = Modifier, titleSize: Int, taglineSize: Int) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        // Logo Placeholder (Icon Gunung/Trail)
        Icon(
            imageVector = Icons.Default.Flag,
            contentDescription = "Logo",
            tint = MantraRed,
            modifier = Modifier.size(64.dp).padding(bottom = 16.dp)
        )

        Text(
            text = "DIRT CHALLENGE",
            fontSize = titleSize.sp,
            fontWeight = FontWeight.Black,
            color = MantraBlue,
            textAlign = TextAlign.Center,
            lineHeight = (titleSize * 1.1).sp
        )

        Text(
            text = "#BERANIKOTOR", // Tagline ala #SudahBasah
            fontSize = taglineSize.sp,
            fontWeight = FontWeight.Bold,
            color = MantraRed,
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

@Composable
fun PrimaryActionButton(text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = { /* TODO: Navigasi */ },
        colors = ButtonDefaults.buttonColors(containerColor = MantraRed),
        shape = RoundedCornerShape(50),
        contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
        modifier = modifier
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

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

@Composable
fun DashboardCard(item: MenuItem, modifier: Modifier = Modifier, isDarkBg: Boolean) {
    Card(
        modifier = modifier
            .aspectRatio(1.5f) // Kotak agak persegi panjang
            .clickable { /* TODO */ },
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkBg) Color.White.copy(alpha = 0.1f) else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isDarkBg) 0.dp else 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                modifier = Modifier.size(32.dp),
                tint = if (isDarkBg) Color.White else MantraRed
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = item.title,
                fontWeight = FontWeight.SemiBold,
                color = if (isDarkBg) Color.White else MantraBlue
            )
        }
    }
}

@Composable
fun FooterSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(MantraBlue),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "RACE MANAGEMENT SYSTEM © 2026",
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 12.sp
        )
    }
}

data class MenuItem(val title: String, val icon: ImageVector)