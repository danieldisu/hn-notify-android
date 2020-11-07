package com.danieldisu.hnnotify.interests

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.danieldisu.hnnotify.common.ErrorView
import com.danieldisu.hnnotify.common.LoadingView
import com.danieldisu.hnnotify.data.interests.Interest
import com.danieldisu.hnnotify.data.interests.KeywordInterest
import com.danieldisu.hnnotify.navigation.NavigationTarget
import com.danieldisu.hnnotify.navigation.navigate

@Composable
fun InterestsScreen(
    navController: NavHostController,
    interestsViewModel: InterestsViewModel
) {
    val screenStateHolder = interestsViewModel.stateFlow.collectAsState()
    val state = screenStateHolder.value

    InterestScaffold(
        value = state,
        onInterestClicked = {
            navController.navigate(NavigationTarget.EditInterest(it))
        }
    )
}

@Composable
fun InterestScaffold(
    value: InterestsScreenState,
    onInterestClicked: (interestId: String) -> Unit
) =
    when {
        value.isLoading -> LoadingView()
        value.error != null -> ErrorView(value.error)
        else -> InterestsLoaded(value.interests, onInterestClicked)
    }

@Composable
fun InterestsLoaded(interests: List<Interest>, onInterestClicked: (interestId: String) -> Unit) =
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        interests.forEach {
            interestItemViewFactory(it, onInterestClicked)
            Spacer(Modifier.height(16.dp))
        }
    }

@Composable
private fun interestItemViewFactory(
    interest: Interest,
    onInterestClicked: (interestId: String) -> Unit
) =
    when (interest) {
        is KeywordInterest -> KeywordInterestItemView(interest, onInterestClicked)
    }

@Composable
fun KeywordInterestItemView(interest: KeywordInterest, onInterestClicked: (interestId: String) -> Unit) =
    Surface(
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth().clickable(onClick = { onInterestClicked(interest.id) })
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = interest.name)
        }
    }

