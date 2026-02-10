package com.hit.racemanagement.data.source.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(val email: String, val password: String, val returnSecureToken: Boolean = true)

@Serializable
data class AuthResponse(val localId: String, val idToken: String)