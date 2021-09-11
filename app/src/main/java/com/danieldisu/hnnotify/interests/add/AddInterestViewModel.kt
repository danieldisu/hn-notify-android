package com.danieldisu.hnnotify.interests.add

import androidx.lifecycle.ViewModel
import com.danieldisu.hnnotify.R
import com.danieldisu.hnnotify.common.InputError
import com.danieldisu.hnnotify.common.ResString
import com.danieldisu.hnnotify.common.ScreenState
import com.danieldisu.hnnotify.common.update
import com.danieldisu.hnnotify.interests.add.views.AddInterestNameViewEventListener
import com.danieldisu.hnnotify.interests.add.views.AddKeywordEventListener
import kotlinx.coroutines.flow.MutableStateFlow

class AddInterestViewModel : ViewModel(), AddKeywordEventListener, AddInterestNameViewEventListener {

    val stateFlow: MutableStateFlow<AddInterestScreenState> =
        MutableStateFlow(AddInterestScreenState.AddFirstKeywordStep())

    init {

    }

    override fun onCreateClicked() {

    }

    // Because all steps have an input value we can reuse this function, if this changes we should create a different
    // function for each state
    override fun onCurrentInputValueChanged(value: String) {
        when (val currentState = stateFlow.value) {
            is AddInterestScreenState.AddFirstKeywordStep -> currentState.copy(currentKeyword = value)
            is AddInterestScreenState.AddAnotherKeywordStep -> currentState.copy(currentKeyword = value)
            is AddInterestScreenState.AddInterestNameStep -> currentState.copy(interestName = value)
        }.update(stateFlow)
    }

    override fun onContinueClicked() {
        when (val currentState = stateFlow.value) {
            is AddInterestScreenState.AddFirstKeywordStep -> currentState.onContinueClicked()
            is AddInterestScreenState.AddAnotherKeywordStep -> currentState.onContinueClicked()
            is AddInterestScreenState.AddInterestNameStep -> TODO()
        }.update(stateFlow)
    }

    override fun onAddMoreClicked() {

    }

    override fun onSkipClicked() =
        when (val currentState = stateFlow.value) {
            is AddInterestScreenState.AddFirstKeywordStep -> throw IllegalStateException()
            is AddInterestScreenState.AddAnotherKeywordStep -> currentState.onSkipClicked()
            is AddInterestScreenState.AddInterestNameStep -> throw IllegalStateException()
        }.update(stateFlow)

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
    ) : AddInterestScreenState(inputError) {

        val suggestedName = addedKeywords.first()

    }
}

private fun AddInterestScreenState.AddAnotherKeywordStep.onSkipClicked(): AddInterestScreenState =
    AddInterestScreenState.AddInterestNameStep(addedKeywords = addedKeywords)

private fun AddInterestScreenState.AddFirstKeywordStep.onContinueClicked(): AddInterestScreenState =
    if (currentKeyword.isNotEmpty()) AddInterestScreenState.AddInterestNameStep(addedKeywords = listOf(currentKeyword))
    else this.copy(inputError = InputError(ResString(R.string.error_interest_name_empty)))

private fun AddInterestScreenState.AddAnotherKeywordStep.onContinueClicked(): AddInterestScreenState =
    if (currentKeyword.isNotEmpty()) AddInterestScreenState.AddInterestNameStep(addedKeywords = listOf(currentKeyword))
    else this.copy(inputError = InputError(ResString(R.string.error_interest_name_empty)))
