package com.hit.racemanagement.ui.features.auth.register

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hit.racemanagement.common.state.UiState
import com.hit.racemanagement.ui.shared.RmsScaffold

class RegisterScreen: Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() } // Untuk pesan error

        // 1. INJECT ScreenModel
        val screenModel = koinScreenModel<RegisterScreenModel>()

        // 2. OBSERVASI State
        val state by screenModel.state.collectAsStateWithLifecycle()

        // 3. LOGIKA NAVIGASI (Efek Samping)
        LaunchedEffect(state.registerUIState) {
            when (state.registerUIState) {
                is UiState.Success -> {
                    // Sukses -> Kembali ke Login atau Masuk Dashboard
                    println("Register Sukses! ID: ${(state.registerUIState as UiState.Success).data.id}")
                    navigator.pop()
                }
                is UiState.Error -> {
                    // Gagal -> Tampilkan Snackbar
                    val msg = (state.registerUIState as UiState.Error).errorMessage
                    snackbarHostState.showSnackbar(msg)
                    // Reset state agar error tidak muncul terus saat rotasi layar
                    screenModel.resetState()
                }
                else -> {}
            }
        }

        RmsScaffold(backgroundColor = Color.White) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                // Tampilkan Loading Overlay jika sedang proses
                if (state.registerUIState is UiState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                // Tampilkan Error (Toast/Snackbar sederhana)
                if (state.registerUIState is UiState.Error) {
                    Text(
                        text = (state.registerUIState as UiState.Error).errorMessage,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
                    )
                }

                // Render UI
                if (maxWidth < 800.dp) {
                    RegisterCompact(
                        onBackClick = { navigator.pop() },
                        onLoginClick = { navigator.pop() },
                        screenModel = screenModel,
                        state = state
                    )
                } else {
                    RegisterExpanded(
                        onBackClick = { navigator.pop() },
                        onLoginClick = { navigator.pop() },
                        screenModel = screenModel,
                        state = state
                    )
                }
            }
        }
    }
}