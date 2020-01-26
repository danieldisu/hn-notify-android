package com.danieldisu.hnnotify.presentation.application

import android.app.Application
import com.danieldisu.hnnotify.infrastructure.di.ApplicationDependencyContainer

class HNNotifyApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    initDIContainer()
  }

  private fun initDIContainer() {
    ApplicationDependencyContainer.init(this)
  }
}
