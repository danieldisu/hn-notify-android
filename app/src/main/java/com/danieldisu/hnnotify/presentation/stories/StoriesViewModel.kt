package com.danieldisu.hnnotify.presentation.stories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danieldisu.hnnotify.domain.fetch.FetchNewStoriesUseCase
import com.danieldisu.hnnotify.presentation.stories.state.StoriesViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class StoriesViewModel(
  private val fetchNewStoriesUseCase: FetchNewStoriesUseCase
) : ViewModel() {

  private val viewState: BroadcastChannel<StoriesViewState> = ConflatedBroadcastChannel()

  init {
    viewModelScope.launch {
      val newStories = fetchNewStoriesUseCase()
      viewState.offer(StoriesViewState.Loaded(newStories))
    }
  }

  fun subscribe(storiesUI: StoriesUI) {
    viewModelScope.launch {
      viewState.asFlow()
        .collect {
          storiesUI.update(it)
        }
    }
  }

}

interface StoriesUI {

  fun update(viewState: StoriesViewState)

}
