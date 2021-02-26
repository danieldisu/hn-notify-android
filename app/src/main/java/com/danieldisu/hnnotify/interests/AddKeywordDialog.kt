package com.danieldisu.hnnotify.interests

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.danieldisu.hnnotify.common.InputTextDialog


@Composable
fun AddKeywordDialog(
    onConfirmButtonClick: (keywordValue: String) -> Unit,
): InputTextDialog =
    InputTextDialog(
        inputLabel = "Add keyword",
        rightButtonText = "Add",
        rightButtonAction = onConfirmButtonClick,
        leftButtonText = "Cancel",
        leftButtonAction = {}
    ).apply { build() }

@Preview(showBackground = true)
@Composable
fun AddInterestDialogPreview() {
    MaterialTheme {
        val addKeywordDialog = AddKeywordDialog {}
        addKeywordDialog.show()
    }
}
