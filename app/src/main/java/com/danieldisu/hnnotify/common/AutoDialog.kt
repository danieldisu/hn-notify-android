package com.danieldisu.hnnotify.common

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

class AutoDialog(
    private val rightButtonText: String,
    private val rightButtonAction: () -> Unit,
    private val leftButtonText: String,
    private val leftButtonAction: () -> Unit,
    private val content: @Composable () -> Unit,
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
                Surface(
                    elevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) { DialogBody() }
            }
        }
    }

    @Composable
    private fun DialogBody() {
        Column {
            content()
            Row(modifier = Modifier.align(Alignment.End).padding(8.dp)) {
                DialogButton(buttonLabel = leftButtonText, onButtonClick = {
                    hide()
                    leftButtonAction()
                })
                HorizontalSpacer()
                DialogButton(buttonLabel = rightButtonText, onButtonClick = {
                    hide()
                    rightButtonAction()
                })
            }
        }
    }

    @Composable
    private fun DialogButton(
        buttonLabel: String,
        onButtonClick: () -> Unit,
    ) {
        Button(
            onClick = { onButtonClick() },
            content = { Text(buttonLabel) }
        )
    }
}
