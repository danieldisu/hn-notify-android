package com.danieldisu.hnnotify.interests

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danieldisu.hnnotify.common.ErrorView
import com.danieldisu.hnnotify.common.LoadingView
import com.danieldisu.hnnotify.data.interests.Interest
import com.danieldisu.hnnotify.data.interests.KeywordInterest


@Composable
fun InterestsScreen(
    interestsViewModel: InterestsViewModel,
    addInterestsViewModel: (interestId: String?) -> AddInterestViewModel
) {
    val screenStateHolder = interestsViewModel.stateFlow.collectAsState()
    val state = screenStateHolder.value

    if (state.isShowingAddInterestDialog) {
        AddInterestScreen(addInterestsViewModel(state.editingInterestAdId))
    } else {
        InterestScaffold(
            value = state,
            onInterestClicked = interestsViewModel::onInterestClicked,
            onAddInterestDialogDismiss = interestsViewModel::onAddInterestDialogDismiss,
            onConfirmInterestDialog = interestsViewModel::onConfirmInterestDialog
        )
    }
}

@Composable
fun InterestScaffold(
    value: InterestsScreenState,
    onInterestClicked: () -> Unit,
    onAddInterestDialogDismiss: () -> Unit,
    onConfirmInterestDialog: (InterestDialogState) -> Unit
) =
    when {
        value.isShowingAddInterestDialog ->
            AddInterestDialog(onAddInterestDialogDismiss, onConfirmInterestDialog, onAddInterestDialogDismiss)
        value.isLoading -> LoadingView()
        value.error != null -> ErrorView(value.error)
        else -> InterestsLoaded(value.interests, onInterestClicked)
    }

@Composable
fun InterestsLoaded(interests: List<Interest>, onInterestClicked: () -> Unit) =
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        interests.forEach {
            interestItemViewFactory(it, onInterestClicked)
            Spacer(Modifier.height(16.dp))
        }
    }

@Composable
private fun interestItemViewFactory(
    interest: Interest,
    onInterestClicked: () -> Unit
) =
    when (interest) {
        is KeywordInterest -> KeywordInterestItemView(interest, onInterestClicked)
    }

@Composable
fun KeywordInterestItemView(interest: KeywordInterest, onInterestClicked: () -> Unit) =
    Surface(
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth().clickable(onClick = onInterestClicked)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = interest.name)
        }
    }

