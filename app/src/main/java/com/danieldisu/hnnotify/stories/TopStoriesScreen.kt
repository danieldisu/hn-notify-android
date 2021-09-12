package com.danieldisu.hnnotify.stories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danieldisu.hnnotify.common.feedback.FeedbackLayout
import com.danieldisu.hnnotify.common.feedback.FeedbackLayoutState
import com.danieldisu.hnnotify.data.stories.Story

@Composable
fun TopStoriesScreen(
    topStoriesViewModel: TopStoriesViewModel,
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
    onStoryClick: () -> Unit,
) {
    FeedbackLayout(state = state.toFeedbackLayoutState()) {
        if (state is TopStoriesScreenState.Loaded) {
            TopStoriesLoaded(stories = state.stories, onStoryClick)
        }
    }
}

@Composable
private fun TopStoriesLoaded(stories: List<Story>, onStoryClick: () -> Unit) =
    LazyColumn(
        Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(stories) {
            StoryItemView(it, onStoryClick)
        }
    }

@Composable
private fun StoryItemView(story: Story, onStoryClick: () -> Unit) {
    Surface(
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onStoryClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = story.title)
        }
    }

}

private fun TopStoriesScreenState.toFeedbackLayoutState(): FeedbackLayoutState =
    when (this) {
        is TopStoriesScreenState.Error -> FeedbackLayoutState.Error()
        TopStoriesScreenState.Initial -> FeedbackLayoutState.Success
        is TopStoriesScreenState.Loaded -> FeedbackLayoutState.Success
        TopStoriesScreenState.Loading -> FeedbackLayoutState.Loading()
    }

@Composable
@Preview(showBackground = true)
fun StoryPreview() {
    MaterialTheme {
        StoryItemView(Story("1", "apple does it again"), {})
    }
}

@Composable
@Preview(showBackground = true)
private fun TopStoriesLoadedPreview() {
    val stories = listOf(
        Story("1", "apple do it again"),
        Story("2", "something about android"),
    )

    MaterialTheme {
        TopStoriesLoaded(stories = stories, {})
    }
}
