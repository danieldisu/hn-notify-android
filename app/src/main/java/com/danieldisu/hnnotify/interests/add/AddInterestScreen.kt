package com.danieldisu.hnnotify.interests.add

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.danieldisu.hnnotify.interests.add.views.AddKeywordView

@Composable
fun AddInterestScreen(viewModel: AddInterestViewModel) {
    val state by viewModel.stateFlow.collectAsState()

    AddInterestScaffold(state, viewModel)
}

@Composable
private fun AddInterestScaffold(state: AddInterestScreenState, viewModel: AddInterestViewModel) {
    when (state) {
        is AddInterestScreenState.AddFirstKeywordStep ->
            AddKeywordView(state.currentKeyword, state.inputError, firstKeyword = true, viewModel)
        is AddInterestScreenState.AddAnotherKeywordStep ->
            AddKeywordView(state.currentKeyword, state.inputError, firstKeyword = false, viewModel)
        is AddInterestScreenState.AddInterestNameStep -> TODO()
    }
}

@Preview(showBackground = true)
@Composable
fun AddInterestPreview() {
    MaterialTheme {

    }
}

