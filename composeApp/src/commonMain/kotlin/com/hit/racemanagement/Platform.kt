package com.hit.racemanagement

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform