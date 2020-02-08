package com.danieldisu.hnnotify.presentation.stories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danieldisu.hnnotify.domain.interesting.GetInterestingStoriesUseCase
import com.danieldisu.hnnotify.domain.scan.ScanInterestingStoriesUseCase
import com.danieldisu.hnnotify.presentation.stories.state.StoriesViewState
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class StoriesViewModel(
  private val scanInterestingStoriesUseCase: ScanInterestingStoriesUseCase,
  private val getInterestingStoriesUseCase: GetInterestingStoriesUseCase,
  private val storiesRowMapper: StoriesRowMapper
) : ViewModel() {

  private val viewState: BroadcastChannel<StoriesViewState> = ConflatedBroadcastChannel()

  init {
    listenForInterestingStories()
    scanForNewStories()
  }

  fun subscribe(storiesUI: StoriesUI) = viewModelScope.launch {
    viewState.asFlow()
      .collect {
        storiesUI.update(it)
      }
  }

  private fun listenForInterestingStories() = viewModelScope.launch {
    getInterestingStoriesUseCase
      .get()
      .map { StoriesViewState.Loaded(storiesRowMapper.map(it)) }
      .collect { viewState.offer(it) }
  }

  fun onFabClicked() {
    scanForNewStories()
  }

  private fun scanForNewStories() {
    viewModelScope.launch {
      scanInterestingStoriesUseCase().getStories()
    }
  }

  interface StoriesUI {

    fun update(viewState: StoriesViewState)

  }
}
