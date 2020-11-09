package com.danieldisu.hnnotify.common

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

open class InputTextDialog(
    private val inputLabel: String,
    private val rightButtonText: String,
    private val rightButtonAction: (keywordValue: String) -> Unit,
    private val leftButtonText: String = "Cancel",
    private val leftButtonAction: () -> Unit = {},
) {

    private var inputFieldValue: String = ""

    private val autoDialog = AutoDialog(
        rightButtonText = rightButtonText,
        rightButtonAction = { rightButtonAction(inputFieldValue) },
        leftButtonText = leftButtonText,
        leftButtonAction = leftButtonAction
    ) { DialogContent(inputLabel) { inputFieldValue = it } }

    fun show() = autoDialog.show()

    @Composable
    fun build(): InputTextDialog {
        autoDialog.build()
        return this
    }
}

@Composable
private fun DialogContent(dialogTitle: String, onInputFieldUpdated: (String) -> Unit) {
    var textState by remember { mutableStateOf(TextFieldValue()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        DialogBody(textState, {
            textState = it
            onInputFieldUpdated(it.text)
        }, dialogTitle)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun DialogBody(textState: TextFieldValue, onValueChange: (TextFieldValue) -> Unit, dialogTitle: String) {
    Column {
        Row { OutlinedTextField(value = textState, label = inputLabel(dialogTitle), onValueChange = onValueChange) }
    }

}

private fun inputLabel(dialogTitle: String): @Composable () -> Unit = { Text(dialogTitle) }
