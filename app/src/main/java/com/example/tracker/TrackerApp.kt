package com.example.tracker

import android.app.Application
import com.example.tracker.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TrackerApp)
            androidLogger()
            modules(appModule)
        }
    }
}