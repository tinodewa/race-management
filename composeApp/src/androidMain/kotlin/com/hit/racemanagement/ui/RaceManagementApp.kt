package com.hit.racemanagement.ui

import android.app.Application
import com.hit.racemanagement.di.initKoin
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class RaceManagementApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initializes Logging for Android (Logcat)
        Napier.base(DebugAntilog())

        initKoin{
            androidContext(this@RaceManagementApp)
            androidLogger()
        }
    }
}
