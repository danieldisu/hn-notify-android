package com.danieldisu.hnnotify.interests.add

import androidx.lifecycle.ViewModel
import com.danieldisu.hnnotify.R
import com.danieldisu.hnnotify.common.InputError
import com.danieldisu.hnnotify.common.ResString
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
            is AddInterestScreenState.AddFirstKeywordStep -> currentState.copy(currentKeyword = value)
            is AddInterestScreenState.AddAnotherKeywordStep -> currentState.copy(currentKeyword = value)
            is AddInterestScreenState.AddInterestNameStep -> currentState.copy(interestName = value)
        }.update(stateFlow)
    }

    fun onKeyboardSubmit() {

    }

    fun onContinueClicked() {
        when (val currentState = stateFlow.value) {
            is AddInterestScreenState.AddFirstKeywordStep -> currentState.onAddFirstKeywordStepContinue()
            is AddInterestScreenState.AddAnotherKeywordStep -> TODO()
            is AddInterestScreenState.AddInterestNameStep -> TODO()
        }.update(stateFlow)
    }

    fun onAddMoreClicked() {

    }

}

sealed class AddInterestScreenState(
    open val inputError: InputError?
) : ScreenState {
    data class AddFirstKeywordStep(
        val currentKeyword: String = "",
        override val inputError: InputError? = null,
    ) : AddInterestScreenState(inputError)

    data class AddAnotherKeywordStep(
        val currentKeyword: String = "",
        val addedKeywords: List<String>,
        override val inputError: InputError? = null,
    ) : AddInterestScreenState(inputError)

    data class AddInterestNameStep(
        val interestName: String = "",
        val addedKeywords: List<String>,
        override val inputError: InputError? = null,
    ) : AddInterestScreenState(inputError)
}

private fun AddInterestScreenState.AddFirstKeywordStep.onAddFirstKeywordStepContinue(): AddInterestScreenState =
    if (currentKeyword.isNotEmpty()) AddInterestScreenState.AddInterestNameStep(addedKeywords = listOf(currentKeyword))
    else this.copy(inputError = InputError(ResString(R.string.error_interest_name_empty)))
