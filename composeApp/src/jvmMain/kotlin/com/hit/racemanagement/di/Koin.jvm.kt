package com.hit.racemanagement.di

import com.hit.racemanagement.data.repositories.AuthRepository
import com.hit.racemanagement.data.repositories.DesktopAuthRepository
import com.hit.racemanagement.platform.DesktopNetworkMonitor
import com.hit.racemanagement.platform.NetworkMonitor
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module= module {
    single<NetworkMonitor> { DesktopNetworkMonitor() }

     single<AuthRepository> { DesktopAuthRepository() }
}