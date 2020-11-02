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
import androidx.compose.ui.window.Dialog
import androidx.ui.tooling.preview.Preview

@Composable
fun AddInterestDialog(
    onDismiss: () -> Unit,
    onConfirmButtonClick: (InterestDialogState) -> Unit,
    onCancelButtonClick: () -> Unit
) =
    Dialog(
        onDismissRequest = onDismiss
    ) { DialogContent(onConfirmButtonClick, onCancelButtonClick) }

@Composable
private fun DialogContent(onConfirmButtonClick: (InterestDialogState) -> Unit, onCancelButtonClick: () -> Unit) {
    var textState by remember { mutableStateOf(TextFieldValue()) }

    Surface(
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row { DialogBody(textState) { textState = it } }
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
    onConfirmButtonClick: (InterestDialogState) -> Unit,
    textState: TextFieldValue
) {
    Button(
        onClick = { onConfirmButtonClick(InterestDialogState(name = textState.text, emptyList())) },
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

data class InterestDialogState(
    val name: String?,
    val interests: List<String>
)

@Composable
private fun DialogBody(textState: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    Column {
        Row { Text(text = "Add an interest", style = MaterialTheme.typography.h6) }
        Spacer(modifier = Modifier.height(16.dp))
        Row { OutlinedTextField(value = textState, label = label(), onValueChange = onValueChange) }
    }

}

private fun label(): @Composable () -> Unit = { Text("Interest Name") }


@Preview(showBackground = true)
@Composable
fun AddInterestDialogPreview() {
    MaterialTheme {
        DialogContent({}, {})
    }
}
