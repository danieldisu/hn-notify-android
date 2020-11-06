package com.danieldisu.hnnotify.interests

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.danieldisu.hnnotify.common.AutoDialog


class AddInterestDialog(
    private val title: String,
    private val onConfirmButtonClick: (AddInterestDialogState) -> Unit,
    private val onCancelButtonClick: () -> Unit,
) {

    private val autoDialog = AutoDialog { DialogContent(title, onConfirmButtonClick, onCancelButtonClick) }

    @Composable
    fun build(
    ): AddInterestDialog {
        autoDialog.build()
        return this
    }

    fun show() = autoDialog.show()

    fun hide() = autoDialog.hide()
}

@Composable
private fun DialogContent(
    dialogTitle: String,
    onConfirmButtonClick: (AddInterestDialogState) -> Unit,
    onCancelButtonClick: () -> Unit,
) {
    var textState by remember { mutableStateOf(TextFieldValue()) }

    Surface(
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row { DialogBody(textState, { textState = it }, dialogTitle) }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.align(Alignment.End)) {
                CancelButton(onCancelButtonClick)
                Spacer(modifier = Modifier.width(10.dp))
                CreateButton(onConfirmButtonClick, textState)
            }
        }
    }
}

@Composable
private fun CreateButton(
    onConfirmButtonClick: (AddInterestDialogState) -> Unit,
    textState: TextFieldValue
) {
    Button(
        onClick = { onConfirmButtonClick(AddInterestDialogState(textInput = textState.text)) },
        content = { Text("Create") }
    )
}

@Composable
private fun CancelButton(onCancelButtonClick: () -> Unit) {
    Button(
        onClick = onCancelButtonClick,
        content = { Text("Cancel") }
    )
}

data class AddInterestDialogState(
    val textInput: String?
)

@Composable
private fun DialogBody(textState: TextFieldValue, onValueChange: (TextFieldValue) -> Unit, dialogTitle: String) {
    Column {
        Row { Text(text = dialogTitle, style = MaterialTheme.typography.h6) }
        Spacer(modifier = Modifier.height(16.dp))
        Row { OutlinedTextField(value = textState, label = label(), onValueChange = onValueChange) }
    }

}

private fun label(): @Composable () -> Unit = { Text("Interest Name") }


@Preview(showBackground = true)
@Composable
fun AddInterestDialogPreview() {
    MaterialTheme {
        DialogContent("Add an interest", {}, {})
    }
}
