package com.danieldisu.hnnotify.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.Dialog

class AutoDialog(
    private val content: @Composable () -> Unit
) {

    private val showing: MutableState<Boolean> = mutableStateOf(false)

    fun show() {
        showing.value = true
    }

    fun hide() {
        showing.value = false
    }

    @Composable
    fun build() {
        if (showing.value) {
            Dialog(onDismissRequest = { hide() }) {
                content()
            }
        }
    }
}
