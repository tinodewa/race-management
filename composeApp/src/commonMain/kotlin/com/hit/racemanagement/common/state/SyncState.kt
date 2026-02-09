package com.hit.racemanagement.common.state

// Represents the synchronization state of an entity
// DELETE => The entity is marked for deletion
// SYNCED => The entity is synchronized with the server
// PENDING => The entity has local changes that need to be synchronized
// FAILED => The last synchronization attempt failed
enum class SyncState {
    DELETE,
    SYNCED,
    PENDING,
    FAILED
}