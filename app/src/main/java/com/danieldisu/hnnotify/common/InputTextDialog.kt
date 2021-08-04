package com.danieldisu.hnnotify.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun InputTextDialog(
    title: String,
    hint: String,
    initialTextValue: String = "",
    rightButtonText: String,
    rightButtonAction: (currentText: String) -> Unit,
    leftButtonText: String = "Cancel",
    leftButtonAction: () -> Unit = {},
    onDismiss: (currentText: String) -> Unit = {},
) {
    val (textState, updateTextState) = remember { mutableStateOf(TextFieldValue(initialTextValue)) }

    Dialog(onDismissRequest = { onDismiss(textState.text) }) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .padding(16.dp)
        ) {
            InputTextDialogTitle(title = title)
            InputTextDialogBody(textState = textState, hint = hint, updateTextState)
            InputTextDialogButtons(
                rightButtonText = rightButtonText,
                rightButtonAction = { rightButtonAction(textState.text) },
                leftButtonText = leftButtonText,
                leftButtonAction = leftButtonAction
            )
        }
    }
}

@Composable
private fun InputTextDialogTitle(
    title: String
) {
    Text(text = title, style = MaterialTheme.typography.h5)
}

@Composable
private fun InputTextDialogButtons(
    rightButtonText: String,
    rightButtonAction: () -> Unit,
    leftButtonText: String = "Cancel",
    leftButtonAction: () -> Unit = {},
) {
    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
        Button(onClick = leftButtonAction, modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(text = leftButtonText)
        }
        Button(onClick = rightButtonAction, modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(text = rightButtonText)
        }
    }
}

@Composable
private fun InputTextDialogBody(textState: TextFieldValue, hint: String, onValueChange: (TextFieldValue) -> Unit) {
    val requester = FocusRequester()

    Column {
        Box(modifier = Modifier.padding(12.dp)) {
            OutlinedTextField(
                value = textState,
                label = { Text(hint) },
                onValueChange = onValueChange,
                modifier = Modifier.focusRequester(requester)
            )
        }
    }

    SideEffect {
        requester.requestFocus()
    }
}

@Preview(showBackground = true)
@Composable
private fun InputTextDialogPreview() {
    InputTextDialog(
        title = "Dialog Title",
        initialTextValue = "this is the initial text value",
        hint = "What should the user input here",
        rightButtonText = "Add",
        rightButtonAction = {},
        leftButtonText = "Cancel",
        leftButtonAction = {},
        onDismiss = {}
    )
}
