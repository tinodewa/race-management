package com.hit.racemanagement.data.model

import kotlinx.serialization.Serializable

@Serializable // Agar mudah diubah jadi JSON nanti
data class Racer(
    val id: String = "", // ID dari Firebase Auth (UID)
    val fullName: String,
    val racerNumber: String, // "116"
    val teamName: String,
    val vehicleModel: String, // "CRF 150L"
    val email: String,
    val role: String = "racer", // "racer", "marshal", "committee"
    val registeredAt: String, // Timestamp
)