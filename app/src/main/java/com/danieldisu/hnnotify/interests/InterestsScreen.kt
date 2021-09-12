package com.danieldisu.hnnotify.interests

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.danieldisu.hnnotify.common.feedback.FeedbackLayout
import com.danieldisu.hnnotify.data.interests.Interest
import com.danieldisu.hnnotify.navigation.NavigationRoute
import com.danieldisu.hnnotify.navigation.navigate

@Composable
fun InterestsScreen(
    interestsViewModel: InterestsViewModel,
    navController: NavController,
) {
    val screenStateHolder = interestsViewModel.stateFlow.collectAsState()
    val state = screenStateHolder.value

    InterestScaffold(
        value = state,
        onInterestClicked = { interestId ->
            navController.navigate(NavigationRoute.EditInterest(interestId))
        }
    )
}

@Composable
private fun InterestScaffold(
    value: InterestsScreenState,
    onInterestClicked: (interestId: String) -> Unit,
) {
    FeedbackLayout(
        isError = value.error != null,
        isEmpty = value.noInterestsLoaded,
        isLoading = value.isLoading
    ) {
        InterestsLoaded(value.interests, onInterestClicked)
    }
}

@Composable
private fun InterestsLoaded(interests: List<Interest>, onInterestClicked: (interestId: String) -> Unit) =
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(interests) {
            KeywordInterestItemView(it, onInterestClicked)
        }
    }

@Composable
private fun KeywordInterestItemView(interest: Interest, onInterestClicked: (interestId: String) -> Unit) =
    Surface(
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onInterestClicked(interest.id) })
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = interest.name)
        }
    }

