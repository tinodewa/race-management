package com.hit.racemanagement.platform

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.InetSocketAddress
import java.net.Socket

/**
 * Desktop implementation of [NetworkMonitor] that tracks internet connectivity status.
 *
 * This monitor periodically checks for network availability by attempting to establish
 * a socket connection to a reliable external host (Google DNS) at regular intervals.
 *
 * @property isOnline A [Flow] that emits `true` when the internet connection is available
 * and `false` otherwise. It polls for the connection status every 5 seconds.
 */
class DesktopNetworkMonitor : NetworkMonitor {

    override val isOnline: Flow<Boolean> = flow {
        // Emit initial state immediately
        emit(checkConnection())

        // Poll every 5 seconds
        while (true) {
            val hasInternet = checkConnection()
            emit(hasInternet)
            delay(5000)
        }
    }

    /**
     * Checks for internet connectivity by attempting to establish a socket connection
     * to Google's Public DNS (8.8.8.8) on port 53.
     *
     * @return `true` if the connection is successful within the 1500ms timeout,
     * `false` otherwise.
     */
    private fun checkConnection(): Boolean {
        return try {
            // Try to connect to Google DNS (8.8.8.8) on port 53 (DNS)
            // It's faster than HTTP and very reliable.
            val socket = Socket()
            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500) // 1.5s timeout
            socket.close()
            true
        } catch (e: Exception) {
            false
        }
    }
}