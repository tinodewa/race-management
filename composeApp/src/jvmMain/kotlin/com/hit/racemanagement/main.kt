package com.hit.racemanagement

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.hit.racemanagement.di.initKoin
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import java.io.InputStream

fun main() = application {
    // 1. Inisialisasi Firebase Admin SDK (Hanya sekali di awal)
    initializeFirebaseAdmin()

    // Initializes Logging for Desktop (System.out)
    Napier.base(DebugAntilog())

    // Init Koin
    initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "RaceManagement",
    ) {
        App()
    }
}

fun initializeFirebaseAdmin() {
    try {
        // Cek apakah sudah ada app yang jalan (biar tidak error double init)
        if (FirebaseApp.getApps().isEmpty()) {

            // Baca file dari folder resources
            val serviceAccount: InputStream? = Thread.currentThread().contextClassLoader
                .getResourceAsStream("service-account.json")

            if (serviceAccount == null) {
                println("❌ ERROR: File service-account.json tidak ditemukan di resources!")
                return
            }

            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                // Opsional: Jika butuh Storage, tambahkan ini:
                // .setStorageBucket("project-id.appspot.com")
                .build()

            FirebaseApp.initializeApp(options)
            println("🔥 Firebase Admin SDK (Desktop) Berhasil Terhubung!")
        }
    } catch (e: Exception) {
        println("❌ Gagal inisialisasi Firebase Desktop: ${e.message}")
        e.printStackTrace()
    }
}