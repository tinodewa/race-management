package com.hit.racemanagement.data.repositories

import com.hit.racemanagement.data.model.Racer

interface AuthRepository {
    // Fungsi Register: Membuat Akun Auth + Menyimpan Data Racer ke Firestore
    suspend fun registerRacer(password: String, racer: Racer): Result<String>

    // Fungsi Login: Mengembalikan User ID jika sukses
    suspend fun login(email: String, password: String): Result<String>

    // Fungsi Cek Role (Opsional, nanti berguna untuk dashboard)
    suspend fun getUserRole(userId: String): Result<String>
}