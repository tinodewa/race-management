package com.hit.racemanagement.common.state

/**
 * Represents the current status of a network or system connection within the railway application.
 *
 * This enum defines the various phases of a connection's lifecycle, typically used to
 * monitor the connectivity between components such as the client, server, or hardware sensors.
 */
enum class ConnectionState {
    OFFLINE,
    SYNCING,
    ERROR,
    SYNCED
}