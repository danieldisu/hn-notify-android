package com.danieldisu.hnnotify.interests

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.danieldisu.hnnotify.common.ErrorView
import com.danieldisu.hnnotify.common.LoadingView
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
fun InterestScaffold(
    value: InterestsScreenState,
    onInterestClicked: (interestId: String) -> Unit,
) =
    when {
        value.isLoading -> LoadingView()
        value.error != null -> ErrorView(value.error)
        else -> InterestsLoaded(value.interests, onInterestClicked)
    }

@Composable
fun InterestsLoaded(interests: List<Interest>, onInterestClicked: (interestId: String) -> Unit) =
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        interests.forEach {
            KeywordInterestItemView(it, onInterestClicked)
            Spacer(Modifier.height(16.dp))
        }
    }

@Composable
fun KeywordInterestItemView(interest: Interest, onInterestClicked: (interestId: String) -> Unit) =
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

