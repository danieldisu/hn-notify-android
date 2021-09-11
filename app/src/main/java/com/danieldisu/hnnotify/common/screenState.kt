package com.danieldisu.hnnotify.common

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * This is a mark interface so we can create extension functions on all the screen states of the app
 */
interface ScreenState

inline fun <reified S : ScreenState> S.update(stateFlow: MutableStateFlow<S>) {
    stateFlow.value = this
    Log.d(S::class.simpleName, "UpdatedScreenState $this")
}

inline fun <reified S : ScreenState> MutableStateFlow<S>.update(newState: S) {
    this.value = newState
    Log.d(S::class.simpleName, "UpdatedScreenState $newState")
}
