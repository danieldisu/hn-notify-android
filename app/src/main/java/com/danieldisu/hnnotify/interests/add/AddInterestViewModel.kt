package com.danieldisu.hnnotify.interests.add

import androidx.lifecycle.ViewModel
import com.danieldisu.hnnotify.common.ScreenState
import com.danieldisu.hnnotify.common.update
import kotlinx.coroutines.flow.MutableStateFlow

class AddInterestViewModel : ViewModel() {

    val stateFlow: MutableStateFlow<AddInterestScreenState> =
        MutableStateFlow(AddInterestScreenState.AddFirstKeywordStep())

    init {

    }

    // Because all steps have an input value we can reuse this function, if this changes we should create a different
    // function for each state
    fun onCurrentInputValueChanged(value: String) {
        when (val currentState = stateFlow.value) {
            is AddInterestScreenState.AddAnotherKeywordStep -> currentState.copy(currentKeyword = value)
            is AddInterestScreenState.AddFirstKeywordStep -> currentState.copy(currentKeyword = value)
            is AddInterestScreenState.AddInterestNameStep -> currentState.copy(interestName = value)
        }.update(stateFlow)
    }

    fun onKeyboardSubmit() {

    }

    fun onContinueClicked() {

    }

    fun onAddMoreClicked() {

    }

}

sealed class AddInterestScreenState : ScreenState {
    data class AddFirstKeywordStep(
        val currentKeyword: String = ""
    ) : AddInterestScreenState()

    data class AddAnotherKeywordStep(
        val currentKeyword: String = "",
        val addedKeywords: List<String>,
    ) : AddInterestScreenState()

    data class AddInterestNameStep(
        val interestName: String = "",
        val addedKeywords: List<String>,
    ) : AddInterestScreenState()
}
