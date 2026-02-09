package com.hit.racemanagement.platform

import kotlinx.coroutines.flow.Flow

/**
 * Utility for monitoring the device's network connectivity status.
 */
interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}