package com.danieldisu.hnnotify.interests.add

import androidx.activity.compose.BackHandler
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.danieldisu.hnnotify.interests.add.views.AddKeywordView
import com.danieldisu.hnnotify.interests.add.views.ChooseInterestNameView

@Composable
fun AddInterestScreen(viewModel: AddInterestViewModel) {
    val state by viewModel.stateFlow.collectAsState()

    AddInterestScaffold(state, viewModel)
}

@Composable
private fun AddInterestScaffold(state: AddInterestScreenState, viewModel: AddInterestViewModel) {
    AddInterestBackHandler(state, viewModel)
    when (state) {
        is AddInterestScreenState.AddFirstKeywordStep ->
            AddKeywordView(state.currentKeyword, state.inputError, firstKeyword = true, viewModel)
        is AddInterestScreenState.AddAnotherKeywordStep ->
            AddKeywordView(state.currentKeyword, state.inputError, firstKeyword = false, viewModel)
        is AddInterestScreenState.AddInterestNameStep ->
            ChooseInterestNameView(state.suggestedName, viewModel)
    }
}

@Composable
fun AddInterestBackHandler(state: AddInterestScreenState, viewModel: AddInterestViewModel) {
    BackHandler(enabled = state.shouldHandleBackButton) { viewModel.onBackPressed() }
}

@Preview(showBackground = true)
@Composable
fun AddInterestPreview() {
    MaterialTheme {

    }
}

