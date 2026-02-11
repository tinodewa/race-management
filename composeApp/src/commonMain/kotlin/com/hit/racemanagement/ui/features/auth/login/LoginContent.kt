package com.hit.racemanagement.ui.features.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hit.racemanagement.ui.features.auth.login.component.LoginFormSection
import com.hit.racemanagement.ui.shared.RmsScaffold
import com.hit.racemanagement.ui.theme.PrimaryRed
import com.hit.racemanagement.ui.theme.SecondaryBlue

// --- TAMPILAN MOBILE (Compact) ---
@Composable
fun LoginCompact(
    onBackClick: () -> Unit,
    isLoading: Boolean,
    onLoginClick: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tombol Back
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Header
        Text(
            text = "DIRT CHALLENGE",
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = SecondaryBlue
        )
        Text(
            text = "Sign in to continue racing",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp, bottom = 48.dp)
        )

        // Form Section
        LoginFormSection(
            isLoading = isLoading,
            onLoginClick = onLoginClick
        )
    }
}

// --- TAMPILAN DESKTOP (Expanded - Split View) ---
@Composable
fun LoginExpanded(
    onBackClick: () -> Unit,
    isLoading: Boolean,
    onLoginClick: (String, String) -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        // Kiri: Visual / Hero Image
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(PrimaryRed, Color(0xFF8E0000))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "DIRT CHALLENGE",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = (48 * 1.1).sp
                )
                Text(
                    text = "Sign in to continue racing",
                    fontSize = 18.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            // Tombol Back Floating di pojok kiri atas
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(24.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
        }

        // Kanan: Form Login
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 450.dp) // Batasi lebar agar tidak terlalu panjang
                    .padding(48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Login",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = SecondaryBlue,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                LoginFormSection(
                    isLoading = isLoading,
                    onLoginClick = onLoginClick
                )
            }
        }
    }
}