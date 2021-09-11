package com.danieldisu.hnnotify.interests.add

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danieldisu.hnnotify.R
import com.danieldisu.hnnotify.common.HorizontalSpacer
import com.danieldisu.hnnotify.common.VerticalSpacer

@Composable
fun AddInterestScreen(viewModel: AddInterestViewModel) {
    val state by viewModel.stateFlow.collectAsState()

    AddInterestScaffold(state, viewModel)
}

@Composable
private fun AddInterestScaffold(state: AddInterestScreenState, viewModel: AddInterestViewModel) {
    when (state) {
        is AddInterestScreenState.AddFirstKeywordStep -> AddKeywords(viewModel)
        is AddInterestScreenState.AddAnotherKeywordStep -> TODO()
        is AddInterestScreenState.AddInterestNameStep -> TODO()
    }

}

@Composable
private fun AddKeywords(viewModel: AddInterestViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        AddKeywordForm(viewModel)
        ButtonRow(viewModel)
    }
}

@Composable
private fun BoxScope.ButtonRow(viewModel: AddInterestViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
    ) {
        AddAnotherButton(viewModel)
        HorizontalSpacer(8)
        ContinueButton(viewModel)
    }
}

@Composable
private fun RowScope.ContinueButton(viewModel: AddInterestViewModel) {
    Button(
        modifier = Modifier.Companion.weight(1f),
        onClick = viewModel::onContinueClicked
    ) {
        Text(text = "Continue")
    }
}

@Composable
private fun RowScope.AddAnotherButton(viewModel: AddInterestViewModel) {
    Button(
        modifier = Modifier.Companion.weight(1f),
        onClick = viewModel::onAddMoreClicked
    ) {
        Text(text = "Add another")
    }
}

@Composable
private fun AddKeywordForm(viewModel: AddInterestViewModel) {
    Column {
        AddInterestKeywordHelpText()
        VerticalSpacer()
        KeywordTextInput(viewModel)
    }
}

@Composable
private fun KeywordTextInput(viewModel: AddInterestViewModel) {
    val (textState, updateTextState) = remember { mutableStateOf(TextFieldValue()) }

    OutlinedTextField(
        value = textState,
        label = { Text(stringResource(id = R.string.hint_add_interest_keyword)) },
        onValueChange = {
            updateTextState(it)
            viewModel.onCurrentInputValueChanged(it.text)
        },
        modifier = Modifier
            .fillMaxWidth()
            .onKeyEvent {
                viewModel.onKeyboardSubmit()
                true
            }
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

    }
}

