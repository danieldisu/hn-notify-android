package com.danieldisu.hnnotify.interests.add.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danieldisu.hnnotify.R
import com.danieldisu.hnnotify.common.HorizontalSpacer
import com.danieldisu.hnnotify.common.InputError
import com.danieldisu.hnnotify.common.RawString
import com.danieldisu.hnnotify.common.VerticalSpacer
import com.danieldisu.hnnotify.common.asString

interface AddKeywordEventListener {
    fun onSkipClicked()
    fun onContinueClicked()
    fun onCurrentInputValueChanged(value: String)
    fun onAddMoreClicked()
}

@Composable
internal fun AddKeywordView(
    currentKeywordValue: String,
    inputError: InputError?,
    firstKeyword: Boolean,
    eventListener: AddKeywordEventListener
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        KeywordForm(inputError, firstKeyword, eventListener)
        ButtonRow(currentKeywordValue, eventListener)
    }
}

@Composable
private fun BoxScope.ButtonRow(currentKeywordValue: String, eventListener: AddKeywordEventListener) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
    ) {
        if (currentKeywordValue.isEmpty()) {
            SkipButton(eventListener)
        } else {
            AddAnotherButton(eventListener)
        }
        HorizontalSpacer(8)
        ContinueButton(eventListener)
    }
}

@Composable
fun RowScope.SkipButton(eventListener: AddKeywordEventListener) {
    Button(
        modifier = Modifier.Companion.weight(1f),
        onClick = eventListener::onSkipClicked
    ) {
        Text(text = stringResource(id = R.string.action_skip))
    }
}

@Composable
private fun RowScope.ContinueButton(eventListener: AddKeywordEventListener) {
    Button(
        modifier = Modifier.Companion.weight(1f),
        onClick = eventListener::onContinueClicked
    ) {
        Text(text = stringResource(id = R.string.action_continue))
    }
}

@Composable
private fun RowScope.AddAnotherButton(eventListener: AddKeywordEventListener) {
    Button(
        modifier = Modifier.Companion.weight(1f),
        onClick = eventListener::onAddMoreClicked
    ) {
        Text(text = stringResource(id = R.string.action_add_another_keyword))
    }
}

@Composable
private fun KeywordForm(inputError: InputError?, firstKeyword: Boolean, eventListener: AddKeywordEventListener) {
    Column {
        AddInterestKeywordHelpText(firstKeyword)
        VerticalSpacer()
        KeywordTextInput(inputError, eventListener)
    }
}

@Composable
private fun KeywordTextInput(inputError: InputError?, eventListener: AddKeywordEventListener) {
    val (textState, updateTextState) = remember { mutableStateOf(TextFieldValue()) }

    OutlinedTextField(
        value = textState,
        label = { Text(stringResource(id = R.string.hint_add_interest_keyword)) },
        onValueChange = {
            updateTextState(it)
            eventListener.onCurrentInputValueChanged(it.text)
        },
        modifier = Modifier.fillMaxWidth(),
        isError = inputError != null,
        keyboardActions = KeyboardActions { eventListener.onContinueClicked() },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    )
    if (inputError != null) {
        Text(
            text = inputError.message.asString(),
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
private fun AddInterestKeywordHelpText(firstKeyword: Boolean) {
    if (firstKeyword) {
        Text(
            text = stringResource(id = R.string.label_add_keyword_help_text),
            style = MaterialTheme.typography.h6
        )
    } else {
        Text(
            text = stringResource(id = R.string.label_add_another_keyword_help_text),
            style = MaterialTheme.typography.h6
        )
    }

}

@Preview(name = "Adding first keyword", showBackground = true)
@Composable
private fun AddKeywordsPreview() {
    MaterialTheme {
        AddKeywordView(
            currentKeywordValue = "",
            inputError = null,
            firstKeyword = true,
            eventListener = emptyListener()
        )
    }
}

@Preview(name = "Error adding keyword", showBackground = true)
@Composable
private fun AddKeywordsPreview2() {
    MaterialTheme {
        AddKeywordView(
            currentKeywordValue = "",
            inputError = InputError(RawString("keyword name can't be empty")),
            firstKeyword = true,
            eventListener = emptyListener()
        )
    }
}

@Preview(name = "Adding another keyword", showBackground = true)
@Composable
private fun AddKeywordsPreview3() {
    MaterialTheme {
        AddKeywordView(
            currentKeywordValue = "",
            inputError = null,
            firstKeyword = false,
            eventListener = emptyListener()
        )
    }
}

private fun emptyListener() = object : AddKeywordEventListener {
    override fun onSkipClicked() {
    }

    override fun onContinueClicked() {
    }

    override fun onCurrentInputValueChanged(value: String) {
    }

    override fun onAddMoreClicked() {
    }
}

