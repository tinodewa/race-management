package com.hit.racemanagement.platform

import kotlinx.browser.window
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener

//contoh call viewmodel:
//val isOnline = networkMonitor.isOnline
//    .stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000), true)

//contoh call UI:
//val networkMonitor = koinInject<NetworkMonitor>()
//val isOnline by networkMonitor.isOnline.collectAsState(initial = true)
//
//if (!isOnline) {
//    Text("⚠️ You are OFFLINE", color = Color.Red)
//}

class WebNetworkMonitor : NetworkMonitor {

    override val isOnline: Flow<Boolean> = callbackFlow {
        // 1. Emit status awal saat pertama kali dipanggil
        trySend(window.navigator.onLine)

        // 2. Definisi Listener sebagai Lambda Variable
        // Kita butuh object EventListener agar bisa di-remove nanti saat flow ditutup
        val onlineListener: (Event) -> Unit = {
            trySend(true)
        }

        val offlineListener: (Event) -> Unit = {
            trySend(false)
        }

        // 3. Pasang Event Listener ke Window Browser
        window.addEventListener("online", onlineListener)
        window.addEventListener("offline", offlineListener)

        // 4. Bersihkan Listener saat Flow tidak lagi dikumpulkan (misal user pindah screen)
        awaitClose {
            window.removeEventListener("online", onlineListener)
            window.removeEventListener("offline", offlineListener)
        }
    }
}