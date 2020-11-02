package com.danieldisu.hnnotify.stories

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.danieldisu.hnnotify.common.ErrorView
import com.danieldisu.hnnotify.data.entities.Story

@Composable
fun TopStoriesScreen(
    topStoriesViewModel: TopStoriesViewModel
) {
    val screenState = topStoriesViewModel.stateFlow.collectAsState()

    TopStoriesScaffold(
        state = screenState.value,
        onStoryClick = topStoriesViewModel::onStoryClick
    )
}


@Composable
fun TopStoriesScaffold(
    state: TopStoriesScreenState,
    onStoryClick: () -> Unit
) = when (state) {
    is TopStoriesScreenState.Loaded -> TopStoriesLoaded(stories = state.stories, onStoryClick)
    is TopStoriesScreenState.Error -> ErrorView(state.error)
    TopStoriesScreenState.Loading -> TopStoriesLoading()
    TopStoriesScreenState.Initial -> Surface {}
}

@Composable
fun TopStoriesLoading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(70.dp)
        )
    }
}

@Composable
private fun TopStoriesLoaded(stories: List<Story>, onStoryClick: () -> Unit) =
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        stories.forEach {
            StoryItemView(it, onStoryClick)
            Spacer(Modifier.height(16.dp))
        }
    }

@Composable
private fun StoryItemView(story: Story, onStoryClick: () -> Unit) {
    Surface(
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth().clickable(onClick = onStoryClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = story.title)
        }
    }

}

@Composable
@Preview(showBackground = true)
fun StoryPreview() {
    MaterialTheme {
        StoryItemView(Story("1", "apple does it again"), {})
    }
}

@Composable
//@Preview(showBackground = true)
private fun TopStoriesLoadedPreview() {
    val stories = listOf(
        Story("1", "apple do it again"),
        Story("2", "something about android"),
    )

    MaterialTheme {
        TopStoriesLoaded(stories = stories, {})
    }
}
