@file:Suppress("FunctionName")

package com.danieldisu.hnnotify.infrastructure.logging

import android.util.Log

private val TAG = "YOLODEBUG"
private val TRACE_TAG = "YOLOTRACE"

fun TRACE(text: String) {
  Log.d(TRACE_TAG, text)
}

fun LOG(text: String) {
  Log.d(TAG, text)
}

fun LOG(error: Throwable) {
  Log.d(TAG, null, error)
}
