package com.hit.racemanagement

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.hit.racemanagement.di.initKoin
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.browser.document
import kotlin.js.js
import org.jetbrains.skiko.wasm.onWasmReady

// 1. Definisikan konfigurasi sebagai String JSON biasa
const val firebaseConfigJson = """
{
    apiKey: "AIzaSyAMiNMjdPOaSQhw5U48mYEuD-WoIPlUURo",
    authDomain: "race-management-61b0a.firebaseapp.com",
    projectId: "race-management-61b0a",
    storageBucket: "race-management-61b0a.firebasestorage.app",
    messagingSenderId: "335336839915",
    appId: "1:335336839915:web:847019cd2caf82813f7cae",
    measurementId: "G-8TNTQFKZCQ"
}
"""

// 2. Buat fungsi eksternal untuk inisialisasi (paling aman)
// Fungsi ini akan mengeksekusi JS murni
@OptIn(ExperimentalWasmJsInterop::class)
fun initializeFirebase(configJson: String) {
    js("""
        if (typeof firebase !== 'undefined') {
            try {
                var config = JSON.parse(configJson);
                
                if (!firebase.apps.length) {
                    // 1. Initialize App
                    firebase.initializeApp(config);
                    
                    // 2. Initialize Analytics (Ekuivalen dengan getAnalytics(app))
                    // Pastikan script analytics sudah ada di index.html
                    if (typeof firebase.analytics === 'function') {
                        firebase.analytics(); 
                        console.log("🔥 Firebase Analytics Initialized!");
                    } else {
                        console.warn("⚠️ Firebase Analytics script not loaded.");
                    }

                    console.log("🔥 Firebase Web Initialized Successfully!");
                }
            } catch (e) {
                console.error("Firebase Init Error:", e);
            }
        } else {
            console.error("⚠️ Firebase SDK not found in index.html");
        }
    """)
}

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    // 3. Panggil fungsi inisialisasi SEBELUM UI
    initializeFirebase(firebaseConfigJson)

    // Initializes Logging for Android (Logcat)
    Napier.base(DebugAntilog())

    // Init Koin
    initKoin()

    // 4. Jalankan UI (ComposeViewport)
    // Gunakan onWasmReady untuk memastikan env siap (opsional tapi recommended di template baru)
    onWasmReady {
        val body = document.body ?: return@onWasmReady
        ComposeViewport(body) {
            App()
        }
    }
}