package com.danieldisu.hnnotify

import android.app.Application
import com.danieldisu.hnnotify.application.di.DependencyContainer

class HNNotifyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DependencyContainer.init(this, isDebug = BuildConfig.DEBUG)
    }
}
