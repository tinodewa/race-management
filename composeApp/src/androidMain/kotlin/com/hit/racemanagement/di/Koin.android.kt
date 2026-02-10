package com.hit.racemanagement.di

import org.koin.core.module.Module
import org.koin.dsl.module

// import data.repository.AndroidAuthRepository (Nanti kita buat)

actual val platformModule: Module = module {
    // single<AuthRepository> { AndroidAuthRepository() }
    // (Komentari dulu biar gak error, kita buat filenya di langkah berikutnya)
}