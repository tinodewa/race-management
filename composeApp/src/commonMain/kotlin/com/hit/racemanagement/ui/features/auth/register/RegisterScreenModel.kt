package com.hit.racemanagement.ui.features.auth.register

import cafe.adriel.voyager.core.model.screenModelScope
import com.hit.racemanagement.common.state.UiState
import com.hit.racemanagement.data.model.Racer
import com.hit.racemanagement.data.repositories.AuthRepository
import com.hit.racemanagement.utils.helpers.BaseScreenModel
import kotlinx.coroutines.launch
import kotlin.time.Clock

class RegisterScreenModel(
    private val repository: AuthRepository
): BaseScreenModel<RegisterStates>(RegisterStates()) {

    fun register(
        fullName: String,
        racerNumber: String,
        teamName: String,
        vehicle: String,
        email: String,
        password: String
    ) {
        // Validasi Sederhana
        if (email.isBlank() || password.isBlank() || fullName.isBlank()) {
            setState { copy(registerUIState = UiState.Error(0, "Mohon lengkapi data wajib!")) }
            return
        }

        // Mulai Loading
        setState { copy(registerUIState = UiState.Loading) }

        screenModelScope.launch {
            // Buat Object Racer
            val newRacer = Racer(
                id = "", // Nanti diisi oleh Repository/Firebase
                fullName = fullName,
                racerNumber = racerNumber,
                teamName = teamName,
                vehicleModel = vehicle,
                email = email,
                role = "racer", // Default user adalah racer
                registeredAt = Clock.System.now().toEpochMilliseconds().toString()
            )

            // Panggil Repository
            val result = repository.registerRacer(password, newRacer)

            result.fold(
                onSuccess = { userId ->
                    newRacer.copy(id = userId)
                    setState { copy(registerUIState = UiState.Success(newRacer)) }
                },
                onFailure = { error ->
                    setState { copy(registerUIState = UiState.Error(0, error.message ?: "Gagal Register")) }
                }
            )
        }
    }

    // Reset state jika user kembali ke halaman ini nanti
    fun resetState() {
        setState { copy(registerUIState = UiState.Idle) }
    }

    fun updateUIState(newState: UiState<Racer>) {
        setState { copy(registerUIState = newState) }
    }
}