package com.hit.racemanagement.common.state

/**
 * A sealed class representing different UI states in the application.
 * It encapsulates various states that the UI can be in, such as idle, loading, refreshing, empty,
 * success, or error states. It can optionally carry data for success states.
 *
 * @param T The type of data that may be carried in a success state.
 */
sealed class UiState<out T: Any?> {
    data object Idle: UiState<Nothing>()

    data object Loading: UiState<Nothing>()

    data object Refreshing: UiState<Nothing>()

    data object Empty: UiState<Nothing>()

    data class Success<out T: Any>(val data: T) : UiState<T>()

    data class Error(
        val errorCode: Int? = null,
        val errorMessage: String,
        val errorMessageRes: Int? = null // im about to migrate into this for better localization
    ) : UiState<Nothing>()
}