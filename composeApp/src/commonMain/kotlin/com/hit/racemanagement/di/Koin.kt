package com.hit.racemanagement.di

import com.hit.racemanagement.ui.features.auth.register.RegisterScreenModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

// 1. Fungsi untuk memulai Koin (panggil di main entry point tiap platform)
fun initKoin(config: KoinAppDeclaration = {}) : KoinApplication = startKoin {
    // Execute platform-specific configuration (modules, androidContext, etc.)
    config.invoke(this)

    modules(
        commonModule,    // Modul umum (ViewModel/ScreenModel)
        platformModule, // <--- Ini modul rahasia yang beda tiap platform
    )
}

// 2. Modul Common: Isinya ScreenModel/ViewModel
val commonModule = module {
    factoryOf(::RegisterScreenModel)
}

// 3. Definisi Expect: "Saya harap ada variabel bernama platformModule di setiap platform"
expect val platformModule: Module