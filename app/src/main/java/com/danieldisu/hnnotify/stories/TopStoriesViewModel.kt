package com.danieldisu.hnnotify.stories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danieldisu.hnnotify.data.core.getError
import com.danieldisu.hnnotify.data.stories.StoryRepository
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TopStoriesViewModel(
    private val storyRepository: StoryRepository
) : ViewModel() {

    fun onStoryClick() {

    }

    val stateFlow = MutableStateFlow<TopStoriesScreenState>(TopStoriesScreenState.Initial)

    init {
        stateFlow.value = TopStoriesScreenState.Loading
        viewModelScope.launch {
            when (val result = storyRepository.getInterestingTopStories()) {
                is ApiResult.Success -> stateFlow.value = TopStoriesScreenState.Loaded(result.response)
                is ApiResult.Failure -> stateFlow.value = TopStoriesScreenState.Error(result.getError().cause)
            }
        }
    }
}
