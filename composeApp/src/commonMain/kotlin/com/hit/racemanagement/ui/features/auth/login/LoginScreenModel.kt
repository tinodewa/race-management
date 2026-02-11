package com.hit.racemanagement.ui.features.auth.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.hit.racemanagement.common.state.UiState
import com.hit.racemanagement.data.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginScreenModel(
    private val repository: AuthRepository
) : ScreenModel {

    // Menggunakan UiState Global dengan tipe data LoginSuccessData
    private val _uiState = MutableStateFlow<UiState<LoginSuccessData>>(UiState.Idle)
    val uiState: StateFlow<UiState<LoginSuccessData>> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        // 1. Validasi Input
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = UiState.Error(errorMessage = "Email dan Password wajib diisi!")
            return
        }

        // 2. Set Loading
        _uiState.value = UiState.Loading

        screenModelScope.launch {
            // 3. Proses Login
            val loginResult = repository.login(email, password)

            loginResult.fold(
                onSuccess = { userId ->
                    // 4. Ambil Role (Chained Request)
                    val roleResult = repository.getUserRole(userId)

                    // Default ke "racer" jika gagal ambil role (fail-safe)
                    val role = roleResult.getOrDefault("racer")

                    // 5. Set Success State
                    _uiState.value = UiState.Success(
                        LoginSuccessData(userId = userId, role = role)
                    )
                },
                onFailure = { error ->
                    // 6. Handle Error
                    val userMessage = if (error.message?.contains("INVALID_LOGIN_CREDENTIALS") == true) {
                        "Email atau password salah."
                    } else {
                        error.message ?: "Terjadi kesalahan saat login."
                    }

                    _uiState.value = UiState.Error(errorMessage = userMessage)
                }
            )
        }
    }

    fun resetState() {
        _uiState.value = UiState.Idle
    }
}