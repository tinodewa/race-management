package com.hit.racemanagement.ui.features.auth.register

import com.hit.racemanagement.common.state.UiState
import com.hit.racemanagement.data.model.Racer

data class RegisterStates (
    val fullName: String = "",
    val racerNumber: String = "",
    val teamName: String = "",
    val vehicle: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val registerUIState: UiState<Racer> = UiState.Idle
)