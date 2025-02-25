package com.danieldisu.hnnotify.application.di

import android.content.Context
import com.danieldisu.hnnotify.application.di.modules.DataModule
import com.danieldisu.hnnotify.application.di.modules.DomainModule
import com.danieldisu.hnnotify.application.di.modules.UiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.module

object DependencyContainer {

    private val modules = sequence<Module> {
        yield(DataModule())
        yield(DomainModule())
        yield(UiModule())
        yield(module {
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
