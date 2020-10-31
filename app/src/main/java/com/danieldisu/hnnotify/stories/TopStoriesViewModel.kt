package com.danieldisu.hnnotify.stories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danieldisu.hnnotify.data.stories.StoryRepository
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
            val interestingTopStories = storyRepository.getInterestingTopStories()
            stateFlow.value = TopStoriesScreenState.Loaded(interestingTopStories)
        }
    }
}
