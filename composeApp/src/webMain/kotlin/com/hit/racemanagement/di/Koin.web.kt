package com.hit.racemanagement.di

import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    // single<AuthRepository> { WebAuthRepository() }
}