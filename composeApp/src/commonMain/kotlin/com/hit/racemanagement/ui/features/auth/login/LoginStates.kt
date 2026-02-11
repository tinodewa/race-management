package com.hit.racemanagement.ui.features.auth.login

data class LoginStates (
    val email: String = "",
    val password: String = ""
)

// Data yang dibawa saat UiState.Success
data class LoginSuccessData(
    val userId: String,
    val role: String
)