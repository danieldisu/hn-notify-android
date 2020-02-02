package com.danieldisu.hnnotify.presentation.application

import android.app.Application
import com.danieldisu.hnnotify.infrastructure.di.ApplicationDependencyContainer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HNNotifyApplication : Application() {

  private val testData: TestData by inject()

  override fun onCreate() {
    super.onCreate()

    initDIContainer()

    GlobalScope.launch {
      testData.addTestData()
    }
  }

  private fun initDIContainer() {
    ApplicationDependencyContainer.init(this)
  }
}
