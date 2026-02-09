package com.hit.racemanagement.ui.features.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hit.racemanagement.ui.features.auth.login.LoginScreen
import com.hit.racemanagement.ui.features.dashboard.component.FooterSection
import com.hit.racemanagement.ui.features.dashboard.component.HeroSection
import com.hit.racemanagement.ui.features.dashboard.component.MenuGridSection
import com.hit.racemanagement.ui.shared.PrimaryActionButton
import com.hit.racemanagement.ui.shared.RmsScaffold
import com.hit.racemanagement.ui.theme.OffWhite
import com.hit.racemanagement.ui.theme.PrimaryRed
import com.hit.racemanagement.ui.theme.SecondaryBlue


@Composable
fun DashboardContent() {
    RmsScaffold(
        backgroundColor = OffWhite // Warna khusus Dashboard
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Tentukan breakpoint sederhana (600dp biasanya batas HP/Tablet)
            if (maxWidth < 800.dp) {
                DashboardCompact(maxWidth)
            } else {
                DashboardExpanded(maxWidth)
            }
        }
    }
}

// --- Layout untuk HP (Vertical) ---
@Composable
fun DashboardCompact(width: Dp) {
    val navigator = LocalNavigator.currentOrThrow

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(width)
                .verticalScroll(rememberScrollState()),
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

        // --- TOMBOL LOGIN STRATEGIS (MOBILE) ---
        // Floating di pojok kanan atas, sangat mudah dijangkau
        TextButton(
            onClick = { navigator.push(LoginScreen()) },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Text("LOGIN", fontWeight = FontWeight.Bold, color = SecondaryBlue)
        }
    }
}

// --- Layout untuk Desktop/Web (Horizontal Split) ---
@Composable
fun DashboardExpanded(width: Dp) {
    val navigator = LocalNavigator.currentOrThrow

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        // Kiri: Branding Area (50% layar)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Brush.verticalGradient(listOf(OffWhite, Color(0xFFEFEFEF)))),
            contentAlignment = Alignment.Center
        ) {
            // --- TOMBOL LOGIN STRATEGIS (DESKTOP) ---
            // Ditaruh di pojok kanan atas panel kiri.
            // Tidak mengganggu Hero di tengah, tapi jelas terlihat.
            TextButton(
                onClick = { navigator.push(LoginScreen()) },
                modifier = Modifier
                    .align(Alignment.TopEnd) // Pojok kanan atas dari Panel Kiri
                    .padding(24.dp)
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = PrimaryRed)
                Spacer(modifier = Modifier.width(8.dp))
                Text("LOGIN", fontWeight = FontWeight.Bold, color = PrimaryRed)
            }

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
                .background(SecondaryBlue) // Kontras biru tua di sisi kanan
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
            // KUNCI 2: Bungkus MenuGridSection agar tidak melar berlebihan
            Column(
                modifier = Modifier
                    .widthIn(max = 500.dp) // Batasi lebar maksimal menu
                    .wrapContentHeight()
                    .verticalScroll(rememberScrollState()), // Agar aman di layar laptop pendek
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MenuGridSection(
                    modifier = Modifier.fillMaxWidth(),
                    columns = 2,
                    useDarkTheme = true // Menu jadi terang di atas background gelap
                )
            }
        }
    }
}