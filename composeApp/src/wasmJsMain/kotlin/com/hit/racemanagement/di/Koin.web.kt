package com.hit.racemanagement.di

import com.hit.racemanagement.data.repositories.AuthRepository
import com.hit.racemanagement.data.repositories.WebAuthRepository
import com.hit.racemanagement.platform.NetworkMonitor
import com.hit.racemanagement.platform.WebNetworkMonitor
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<NetworkMonitor> { WebNetworkMonitor() }

     single<AuthRepository> { WebAuthRepository() }
}