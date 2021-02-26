package com.danieldisu.hnnotify.application.di

import android.content.Context
import com.danieldisu.hnnotify.application.di.modules.DataModule
import com.danieldisu.hnnotify.application.di.modules.UiModule
import com.danieldisu.hnnotify.navigation.AppNavigator
import com.danieldisu.hnnotify.navigation.AppNavigatorImpl
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.module

object DependencyContainer {

    private val modules = sequence<Module> {
        yield(DataModule())
        yield(UiModule())
        yield(module {
            factory<AppNavigator> { AppNavigatorImpl }
        })
    }

    fun init(context: Context, isDebug: Boolean) {
        startKoin {
            if (isDebug) {
                androidLogger(Level.ERROR)
            }
            androidContext(context)
            modules(modules.toList())
        }
    }

}
