package com.hit.racemanagement.data.repositories

import com.hit.racemanagement.data.model.Racer
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore

class AndroidAuthRepository : AuthRepository {

    // Referensi ke Auth dan Firestore
    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    override suspend fun registerRacer(password: String, racer: Racer): Result<String> {
        return try {
            // 1. Buat User di Firebase Authentication (Email & Password)
            val authResult = auth.createUserWithEmailAndPassword(racer.email, password)
            val userId = authResult.user?.uid ?: throw Exception("Gagal mendapatkan User ID")

            // 2. Update object Racer dengan ID yang baru didapat
            val newRacer = racer.copy(id = userId)

            // 3. Simpan data lengkap ke Firestore
            // Path: racers/{userId}
            firestore.collection("racers")
                .document(userId)
                .set(newRacer) // GitLive otomatis serialisasi object Racer!

            // 4. Sukses
            Result.success(userId)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Result<String> {
        return try {
            // 1. Sign In
            val authResult = auth.signInWithEmailAndPassword(email, password)
            val userId = authResult.user?.uid ?: throw Exception("User ID null")

            Result.success(userId)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getUserRole(userId: String): Result<String> {
        return try {
            // 1. Ambil dokumen dari Firestore
            val snapshot = firestore.collection("racers").document(userId).get()

            // 2. Convert ke Object Racer
            val racer = snapshot.data<Racer>()

            // 3. Ambil role-nya
            Result.success(racer.role)
        } catch (e: Exception) {
            // Jika error (misal dokumen gak ada), anggap role default "racer" atau error
            Result.failure(e)
        }
    }
}