package com.hit.racemanagement.utils.helpers

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DateTimeHelper {

    /**
     * Formats an ISO 8601 string (e.g. "2026-01-28T00:29:09.112909Z")
     * to "HH:mm dd-MM-yyyy" (e.g. "07:29 28-01-2026").
     *
     * Converts to the user's Local Timezone automatically.
     */
    fun formatIsoToDisplay(isoString: String): String {
        return try {
            // 1. Parse the ISO String to an Instant (UTC)
            val instant = Instant.parse(isoString)

            // 2. Convert to Device's Local TimeZone
            val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

            // 3. Manually format to "HH:mm dd-MM-yyyy"
            // We use padStart(2, '0') to ensure "9" becomes "09"
            val hour = localDateTime.hour.toString().padStart(2, '0')
            val minute = localDateTime.minute.toString().padStart(2, '0')
            val day = localDateTime.dayOfMonth.toString().padStart(2, '0')
            val month = localDateTime.monthNumber.toString().padStart(2, '0')
            val year = localDateTime.year

            "$hour:$minute $day-$month-$year"
        } catch (e: Exception) {
            // Fallback: If parsing fails, just return the raw string or empty
            isoString
        }
    }
}