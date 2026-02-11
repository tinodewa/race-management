package com.hit.racemanagement.ui.features.auth.login

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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

class LoginScreen: Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val snackbarHostState = remember { SnackbarHostState() }

        // 1. Inject Model
        val screenModel = koinScreenModel<LoginScreenModel>()

        // 2. Collect State Global
        val state by screenModel.uiState.collectAsStateWithLifecycle()

        // 3. Handle Side Effects (Navigasi / Snackbar)
        LaunchedEffect(state) {
            when (state) {
                is UiState.Success -> {
                    val data = (state as UiState.Success<LoginSuccessData>).data
                    println("Login Berhasil! UID: ${data.userId}, Role: ${data.role}")
                    navigator.pop()
                    // TOD: Arahkan user berdasarkan Role
                    // if (data.role == "committee") navigator.replace(CommitteeDashboard())
                    // else navigator.replace(RacerDashboard())
                }
                is UiState.Error -> {
                    val msg = (state as UiState.Error).errorMessage
                    snackbarHostState.showSnackbar(msg)
                    screenModel.resetState()
                }
                else -> {} // Idle, Loading, dll tidak perlu side effect khusus
            }
        }
        // Gunakan Wrapper Scaffold kita
        RmsScaffold(backgroundColor = Color.White) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                // Tampilkan Loading Indicator Global
                if (state is UiState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                if (maxWidth < 800.dp) {
                    LoginCompact(
                        onBackClick = { navigator.pop() },
                        isLoading = state is UiState.Loading,
                        onLoginClick = { email, password ->
                            screenModel.login(email, password)
                        }
                    )
                } else {
                    LoginExpanded(
                        onBackClick = { navigator.pop() },
                        isLoading = state is UiState.Loading,
                        onLoginClick = { email, password ->
                            screenModel.login(email, password)
                        }
                    )
                }
            }
        }
    }
}