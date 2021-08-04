package com.danieldisu.hnnotify.interests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.danieldisu.hnnotify.common.InputTextDialog


@Composable
fun AddKeywordDialog(
    onConfirmButtonClick: (keywordValue: String) -> Unit,
    onDismiss: () -> Unit
) {
    InputTextDialog(
        title = "Add a keyword",
        hint = "Something that defines the interest",
        rightButtonText = "Add",
        rightButtonAction = onConfirmButtonClick,
        leftButtonText = "Cancel",
        leftButtonAction = onDismiss,
        onDismiss = { onDismiss() }
    )
}

@Preview(showBackground = true)
@Composable
fun AddInterestDialogPreview() {
    AddKeywordDialog({}, {})
}
