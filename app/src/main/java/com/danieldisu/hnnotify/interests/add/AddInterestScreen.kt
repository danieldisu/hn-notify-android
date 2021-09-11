package com.danieldisu.hnnotify.interests.add

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danieldisu.hnnotify.R
import com.danieldisu.hnnotify.common.HorizontalSpacer
import com.danieldisu.hnnotify.common.VerticalSpacer

@Composable
fun AddInterestScreen() {
    AddInterestScaffold()
}

@Composable
private fun AddInterestScaffold() {
    AddKeywords()
}

@Composable
private fun AddKeywords() {
    val (textState, updateTextState) = remember { mutableStateOf(TextFieldValue()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        AddKeywordForm(textState, updateTextState)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Add another")
            }
            HorizontalSpacer(8)
            Button(
                modifier = Modifier.weight(1f),
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Continue")
            }
        }

    }


}

@Composable
private fun AddKeywordForm(
    textState: TextFieldValue,
    updateTextState: (TextFieldValue) -> Unit
) {
    Column {
        AddInterestKeywordHelpText()
        VerticalSpacer()
        KeywordTextInput(textState, updateTextState)
    }
}

@Composable
private fun KeywordTextInput(
    textState: TextFieldValue,
    updateTextState: (TextFieldValue) -> Unit
) {
    OutlinedTextField(
        value = textState,
        label = { Text(stringResource(id = R.string.hint_add_interest_keyword)) },
        onValueChange = updateTextState,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun AddInterestKeywordHelpText() {
    Text(
        text = stringResource(id = R.string.label_add_interest_help_text),
        style = MaterialTheme.typography.h6
    )
}

@Preview(showBackground = true)
@Composable
fun AddInterestPreview() {
    MaterialTheme {
        AddKeywords()
    }
}

