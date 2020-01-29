package com.danieldisu.hnnotify.presentation.stories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danieldisu.hnnotify.domain.scan.ScanInterestingStoriesUseCase
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
  private val scanInterestingStoriesUseCase: ScanInterestingStoriesUseCase
) : ViewModel() {

  private val viewState: BroadcastChannel<StoriesViewState> = ConflatedBroadcastChannel()

  init {
    loadStories()
  }

  fun subscribe(storiesUI: StoriesUI) {
    viewModelScope.launch {
      viewState.asFlow()
        .collect {
          storiesUI.update(it)
        }
    }
  }

  fun onFabClicked() {
    loadStories()
  }

  private fun loadStories() {
    viewModelScope.launch {
      val newStories = scanInterestingStoriesUseCase()
        .flatMapTo(mutableListOf()) { it.value }
        .distinct()

      if (newStories.isEmpty()) {
        viewState.offer(StoriesViewState.Empty)
      } else {
        viewState.offer(StoriesViewState.Loaded(newStories))
      }
    }
  }

}

interface StoriesUI {

  fun update(viewState: StoriesViewState)

}