package com.danieldisu.hnnotify.navigation

import android.content.ComponentCallbacks
import android.content.res.Configuration
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

object NoRetainViewModelOwner : ViewModelStoreOwner, ComponentCallbacks {
    override fun getViewModelStore(): ViewModelStore = ViewModelStore()

    override fun onConfigurationChanged(p0: Configuration) {

    }

    override fun onLowMemory() {

    }
}
