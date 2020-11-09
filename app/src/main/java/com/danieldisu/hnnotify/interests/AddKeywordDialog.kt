package com.danieldisu.hnnotify.interests

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.danieldisu.hnnotify.common.AutoDialog

class AddKeywordDialog(
    private val inputLabel: String,
    private val onConfirmButtonClick: (keywordValue: String) -> Unit,
    private val onCancelButtonClick: () -> Unit,
) {

    private var inputFieldValue: String = ""

    private val autoDialog = AutoDialog(
        rightButtonText = "Add",
        rightButtonAction = { onConfirmButtonClick(inputFieldValue) },
        leftButtonText = "Cancel",
        leftButtonAction = onCancelButtonClick
    ) { DialogContent(inputLabel) { inputFieldValue = it } }

    fun show() = autoDialog.show()

    @Composable
    private fun build() {
        autoDialog.build()
    }

    companion object {
        @Composable
        fun build(
            inputLabel: String,
            onConfirmButtonClick: (keywordValue: String) -> Unit,
            onCancelButtonClick: () -> Unit,
        ): AddKeywordDialog = AddKeywordDialog(inputLabel, onConfirmButtonClick, onCancelButtonClick)
            .apply { build() }
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


@Preview(showBackground = true)
@Composable
fun AddInterestDialogPreview() {
    MaterialTheme {
        DialogContent("Add an interest") {}
    }
}
