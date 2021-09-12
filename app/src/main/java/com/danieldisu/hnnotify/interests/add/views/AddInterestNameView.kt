package com.danieldisu.hnnotify.interests.add.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danieldisu.hnnotify.R
import com.danieldisu.hnnotify.common.VerticalSpacer
import com.danieldisu.hnnotify.common.rememberTextValue

interface AddInterestNameViewEventListener {
    fun onCreateClicked()
    fun onCurrentInputValueChanged(value: String)
}

@Composable
fun ChooseInterestNameView(interestName: String, keywords: List<String>, eventListener: AddInterestNameViewEventListener) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        InterestNameForm(interestName, keywords, eventListener)
        ButtonRow(eventListener)
    }
}

@Composable
private fun InterestNameForm(interestName: String, keywords: List<String>, eventListener: AddInterestNameViewEventListener) {
    Column {
        AddInterestNameHelpText()
        VerticalSpacer()
        NameTextInput(interestName, eventListener)
        VerticalSpacer()
        KeywordList(keywords)
    }
}

@Composable
private fun NameTextInput(interestName: String, eventListener: AddInterestNameViewEventListener) {
    val (textState, updateTextState) = rememberTextValue(interestName)
    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        value = textState,
        label = { Text(stringResource(id = R.string.hint_add_interest_name)) },
        onValueChange = {
            updateTextState(it)
            eventListener.onCurrentInputValueChanged(it.text)
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        keyboardActions = KeyboardActions { eventListener.onCreateClicked() },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
    )

    SideEffect {
        focusRequester.requestFocus()
    }
}

@Composable
private fun BoxScope.ButtonRow(eventListener: AddInterestNameViewEventListener) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
    ) {
        ContinueButton(eventListener)
    }
}

@Composable
private fun RowScope.ContinueButton(eventListener: AddInterestNameViewEventListener) {
    Button(
        modifier = Modifier.weight(1f),
        onClick = eventListener::onCreateClicked
    ) {
        Text(text = stringResource(id = R.string.action_create_interest))
    }
}

@Composable
private fun AddInterestNameHelpText() {
    Text(
        text = stringResource(id = R.string.label_name_your_interest),
        style = MaterialTheme.typography.h6
    )
}

@Composable
private fun KeywordList(keywords: List<String>) {
    Text(text = stringResource(id = R.string.label_keywords_for_interest_about_to_create))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .verticalScroll(rememberScrollState())
    ) {
        keywords.forEach { keyword ->
            Text(text = buildString {
                append("- ")
                append(keyword)
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChooseInterestNamePreview() {
    MaterialTheme {
        ChooseInterestNameView(
            interestName = "Kotlin",
            keywords = listOf("Kotlin", "JVM"),
            eventListener = emptyListener()
        )
    }
}

private fun emptyListener() = object : AddInterestNameViewEventListener {
    override fun onCreateClicked() {
    }

    override fun onCurrentInputValueChanged(value: String) {
    }
}
