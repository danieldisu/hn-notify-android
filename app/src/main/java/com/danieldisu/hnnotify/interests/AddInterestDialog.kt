package com.danieldisu.hnnotify.interests

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextField
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
        Column {
            Row {
                DialogBody(textState) { textState = it }
            }
            Row(modifier = Modifier.align(Alignment.End).padding(10.dp)) {
                Button(onCancelButtonClick) { Text("Cancel") }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = { onConfirmButtonClick(InterestDialogState(name = textState.text, emptyList())) }
                ) {
                    Text("Create")
                }
            }
        }
    }
}

data class InterestDialogState(
    val name: String?,
    val interests: List<String>
)

@Composable
fun confirmButton(): @Composable () -> Unit = {
    Text("Confirm")
}

@Composable
fun dismissButton(): @Composable () -> Unit = {
    Text("Confirm")
}


@Composable
private fun DialogBody(textState: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row { Text(text = "Add an interest", style = MaterialTheme.typography.h6) }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text(text = "Interest Name")
            TextField(value = textState, onValueChange = onValueChange)
        }
    }

}


@Preview(showBackground = true)
@Composable
fun AddInterestDialogPreview() {
    MaterialTheme {
        DialogContent({}, {})
    }
}
