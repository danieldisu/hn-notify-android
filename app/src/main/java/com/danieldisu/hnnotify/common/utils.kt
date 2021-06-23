package com.danieldisu.hnnotify.common

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> MutableStateFlow<T>.updateState(
    function: (oldState: T) -> T,
) {
    this.value = function(this.value)
}
