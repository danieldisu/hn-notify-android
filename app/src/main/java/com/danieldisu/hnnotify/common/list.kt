package com.danieldisu.hnnotify.common

fun <T> List<T>.pop(): List<T> =
    if (isEmpty()) this
    else minusElement(last())
