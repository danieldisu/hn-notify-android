package com.danieldisu.hnnotify.stories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danieldisu.hnnotify.data.stories.Story
import com.danieldisu.hnnotify.domain.GetUserTopStoriesUseCase
import com.danieldisu.hnnotify.domain.core.GenericUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TopStoriesViewModel(
    private val getUserTopStoriesUseCase: GetUserTopStoriesUseCase,
) : ViewModel() {

    val stateFlow = MutableStateFlow<TopStoriesScreenState>(TopStoriesScreenState.Initial)

    init {
        stateFlow.value = TopStoriesScreenState.Loading
        viewModelScope.launch {
            when (val result = getUserTopStoriesUseCase()) {
                is GenericUseCaseResult.Error -> onGetUserTopStoriesFailure(result.value)
                is GenericUseCaseResult.Success -> onGetUserTopStoriesSuccess(result.value)
            }
        }
    }

    fun onStoryClick() {

    }

    private fun onGetUserTopStoriesFailure(value: Throwable) {
        updateState(TopStoriesScreenState.Error(value))
    }

    private fun onGetUserTopStoriesSuccess(stories: List<Story>) {
        updateState(TopStoriesScreenState.Loaded(stories))
    }

    private fun updateState(newState: TopStoriesScreenState) {
        stateFlow.value = newState
    }
}
