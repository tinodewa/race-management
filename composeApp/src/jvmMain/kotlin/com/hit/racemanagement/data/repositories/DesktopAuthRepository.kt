package com.hit.racemanagement.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserRecord
import com.google.firebase.cloud.FirestoreClient
import com.hit.racemanagement.data.model.Racer
import com.hit.racemanagement.data.source.remote.response.AuthRequest
import com.hit.racemanagement.data.source.remote.response.AuthResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class DesktopAuthRepository : AuthRepository {

    // 1. Inisialisasi Ktor Client untuk Login (REST API)
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    // GANTI DENGAN WEB API KEY ANDA DARI FIREBASE CONSOLE
    private val WEB_API_KEY = "AIzaSyAMiNMjdPOaSQhw5U48mYEuD-WoIPlUURo"

    override suspend fun registerRacer(password: String, racer: Racer): Result<String> {
        return try {
            // A. Create User via Admin SDK (God Mode)
            val request = FirebaseAuth.getInstance().createUser(
                UserRecord.CreateRequest()
                    .setEmail(racer.email)
                    .setPassword(password)
                    .setDisplayName(racer.fullName)
            )
            val uid = request.uid

            // B. Simpan ke Firestore via Admin SDK
            val newRacer = racer.copy(id = uid)

            // Konversi Kotlin Object ke Map agar Admin SDK Java mengerti
            // (Atau bisa pakai library Jackson, tapi manual Map lebih aman tanpa nambah library)
            val racerMap = mapOf(
                "id" to newRacer.id,
                "fullName" to newRacer.fullName,
                "racerNumber" to newRacer.racerNumber,
                "teamName" to newRacer.teamName,
                "vehicleModel" to newRacer.vehicleModel,
                "email" to newRacer.email,
                "role" to newRacer.role,
                "registeredAt" to newRacer.registeredAt
            )

            FirestoreClient.getFirestore().collection("racers").document(uid).set(racerMap).get()

            Result.success(uid)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Result<String> {
        return try {
            // Kita tembak REST API Firebase Auth karena Admin SDK gak bisa login password
            val response: AuthResponse = client.post("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=$WEB_API_KEY") {
                contentType(ContentType.Application.Json)
                setBody(AuthRequest(email = email, password = password))
            }.body()

            Result.success(response.localId)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Exception("Login Failed: ${e.message}"))
        }
    }

    override suspend fun getUserRole(userId: String): Result<String> {
        return try {
            val doc = FirestoreClient.getFirestore().collection("racers").document(userId).get().get()
            if (doc.exists()) {
                val role = doc.getString("role") ?: "racer"
                Result.success(role)
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}