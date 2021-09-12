package com.danieldisu.hnnotify.interests.add

import androidx.activity.compose.BackHandler
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import com.danieldisu.hnnotify.common.feedback.FeedbackLayout
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

    FeedbackLayout(
        isLoading = state.isLoading,
        isError = state.isError,
        showContent = true,
        showTransparentLayer = state.isLoading || state.isError,
        onRetryClicked = viewModel::onRetryClicked,
    ) {

        if (state.isError) {
            LocalSoftwareKeyboardController.current?.hide()
        }

        when (state) {
            is AddInterestScreenState.AddFirstKeywordStep ->
                AddKeywordView(
                    currentKeywordValue = state.currentKeyword,
                    inputError = state.inputError,
                    firstKeyword = true,
                    eventListener = viewModel
                )
            is AddInterestScreenState.AddAnotherKeywordStep ->
                AddKeywordView(
                    currentKeywordValue = state.currentKeyword,
                    inputError = state.inputError, firstKeyword = false,
                    eventListener = viewModel
                )
            is AddInterestScreenState.AddInterestNameStep ->
                ChooseInterestNameView(
                    interestName = state.interestName,
                    keywords = state.addedKeywords,
                    eventListener = viewModel
                )
        }
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

