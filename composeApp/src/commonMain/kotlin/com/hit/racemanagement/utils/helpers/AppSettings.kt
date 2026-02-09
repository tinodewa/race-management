package com.hit.racemanagement.utils.helpers

import com.russhwolf.settings.Settings

class AppSettings(private val settings: Settings) {
    private val KEY_LAST_SYNC = "last_sync_timestamp"
    private val MOCK_USER_NOT_FOUND = "mock_user_not_found"

    // Save Data
    fun saveLastTimeSync(time: String) {
        settings.putString(KEY_LAST_SYNC, time)
    }

    // Read Data
    fun getLastTimeSync(): String? {
        // Returns null if key doesn't exist (wraps getStringOrNull)
        return settings.getStringOrNull(KEY_LAST_SYNC)
    }

    fun setMockUserNotFound(mockPrefix: String) {
        settings.putString(MOCK_USER_NOT_FOUND, mockPrefix)
    }

    fun getMockUserNotFound(): String {
        return settings.getString(MOCK_USER_NOT_FOUND, "")
    }
}