package com.hit.racemanagement.di

import com.hit.racemanagement.data.repositories.AndroidAuthRepository
import com.hit.racemanagement.data.repositories.AuthRepository
import com.hit.racemanagement.platform.AndroidNetworkMonitor
import com.hit.racemanagement.platform.NetworkMonitor
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<NetworkMonitor> { AndroidNetworkMonitor(get()) }

    // Bind Interface ke Implementasi
    single<AuthRepository> { AndroidAuthRepository() }
}