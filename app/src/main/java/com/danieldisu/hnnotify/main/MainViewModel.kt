package com.danieldisu.hnnotify.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel : ViewModel() {

    val stateFlow = MutableStateFlow(MainScreenState())

    fun onHomeIconClick() {
        stateFlow.value = MainScreenState(Tab.TopStories)
    }

    fun onInterestsButtonClick() {
        stateFlow.value = MainScreenState(Tab.Interests)
    }
}

data class MainScreenState(
    val selectedTab: Tab = Tab.TopStories
)

enum class Tab {
    TopStories,
    Interests
}
