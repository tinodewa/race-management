package com.hit.racemanagement.ui.features.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.SportsMotorsports
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hit.racemanagement.ui.features.auth.register.component.RegisterFormSection
import com.hit.racemanagement.ui.theme.PrimaryRed
import com.hit.racemanagement.ui.theme.SecondaryBlue

// --- TAMPILAN MOBILE (Compact) ---
@Composable
fun RegisterCompact(
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    screenModel: RegisterScreenModel,
    state: RegisterStates
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

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "BE A RACER",
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = SecondaryBlue
        )
        Text(
            text = "Join the challenge today",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        RegisterFormSection(
            screenModel = screenModel,
            state = state
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Link ke Login
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Already have an account?", color = Color.Gray)
            TextButton(onClick = onLoginClick) {
                Text("Login", fontWeight = FontWeight.Bold, color = PrimaryRed)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

// --- TAMPILAN DESKTOP (Expanded) ---
@Composable
fun RegisterExpanded(
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    screenModel: RegisterScreenModel,
    state: RegisterStates
) {
    Row(modifier = Modifier.fillMaxSize()) {
        // Kiri: Visual (Sama patternnya dengan Login tapi beda teks/warna)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(SecondaryBlue, Color(0xFF002171)) // Biru dominan untuk Register
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(24.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }

            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.SportsMotorsports,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(80.dp).padding(bottom = 16.dp)
                )
                Text(
                    text = "START YOUR ENGINE",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = (42 * 1.1).sp,
                )
                Text(
                    text = "REGISTER • RACE • WIN",
                    fontSize = 18.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

        // Kanan: Form
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 500.dp)
                    .padding(48.dp)
                    .verticalScroll(rememberScrollState()), // Scrollable jika layar pendek
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Racer Registration",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = SecondaryBlue,
                    lineHeight = (32 * 1.1).sp,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                RegisterFormSection(
                    screenModel = screenModel,
                    state = state
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Already registered?", color = Color.Gray)
                    TextButton(onClick = onLoginClick) {
                        Text("Login Here", fontWeight = FontWeight.Bold, color = PrimaryRed)
                    }
                }
            }
        }
    }
}