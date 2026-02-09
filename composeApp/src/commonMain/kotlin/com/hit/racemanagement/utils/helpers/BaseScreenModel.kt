package com.hit.racemanagement.utils.helpers

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * A base class for [ScreenModel] implementations that follow a unidirectional data flow pattern.
 * Manages a reactive state of type [S] using [StateFlow].
 *
 * @param S The type of the state object managed by this screen model.
 * @param initialState The initial state to be emitted when the screen model is created.
 */
// <S> is the State type for that specific screen
abstract class BaseScreenModel<S>(initialState: S) : ScreenModel {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    // The Magic Helper
    protected fun setState(reducer: S.() -> S) {
        _state.update { oldState -> oldState.reducer() }
    }

    // Optional: Helper to read current state without .value
    protected val currentState: S
        get() = state.value
}