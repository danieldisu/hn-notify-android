package com.danieldisu.hnnotify.interests.add

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danieldisu.hnnotify.R
import com.danieldisu.hnnotify.common.InputError
import com.danieldisu.hnnotify.common.ResString
import com.danieldisu.hnnotify.common.ScreenState
import com.danieldisu.hnnotify.common.pop
import com.danieldisu.hnnotify.common.update
import com.danieldisu.hnnotify.data.interests.InterestRepository
import com.danieldisu.hnnotify.interests.add.views.AddInterestNameViewEventListener
import com.danieldisu.hnnotify.interests.add.views.AddKeywordEventListener
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddInterestViewModel(
    private val interestRepository: InterestRepository,
) : ViewModel(), AddKeywordEventListener, AddInterestNameViewEventListener {

    val stateFlow: MutableStateFlow<AddInterestScreenState> =
        MutableStateFlow(AddInterestScreenState.AddFirstKeywordStep())

    override fun onCreateClicked() {
        val currentState = stateFlow.value as AddInterestScreenState.AddInterestNameStep
        if (currentState.interestName.isEmpty()) {
            currentState.copy(inputError = InputError(ResString(R.string.error_interest_name_empty))).update(stateFlow)
        } else {
            // do the request
            createInterest(currentState)
        }
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
            is AddInterestScreenState.AddInterestNameStep -> throw IllegalStateException()
        }.update(stateFlow)
    }

    override fun onAddMoreClicked() {
        when (val currentState = stateFlow.value) {
            is AddInterestScreenState.AddFirstKeywordStep -> currentState.onAddAnotherKeywordClicked()
            is AddInterestScreenState.AddAnotherKeywordStep -> currentState.onAddAnotherKeywordClicked()
            is AddInterestScreenState.AddInterestNameStep -> throw IllegalStateException()
        }.update(stateFlow)
    }

    override fun onSkipClicked() =
        when (val currentState = stateFlow.value) {
            is AddInterestScreenState.AddFirstKeywordStep -> throw IllegalStateException()
            is AddInterestScreenState.AddAnotherKeywordStep -> currentState.onSkipClicked()
            is AddInterestScreenState.AddInterestNameStep -> throw IllegalStateException()
        }.update(stateFlow)

    fun onBackPressed() =
        when (val currentState = stateFlow.value) {
            is AddInterestScreenState.AddFirstKeywordStep -> throw IllegalStateException()
            is AddInterestScreenState.AddAnotherKeywordStep -> currentState.onBackPressed()
            is AddInterestScreenState.AddInterestNameStep -> currentState.onBackPressed()
        }.update(stateFlow)

    private fun createInterest(currentState: AddInterestScreenState.AddInterestNameStep) = viewModelScope.launch {
        val result = interestRepository.saveInterest("1", currentState.interestName, currentState.addedKeywords)
        when (result) {
            is ApiResult.Success -> {
                Log.d("AddInterest", "Success")
            }
            is ApiResult.Failure -> {
                Log.d("AddInterest", "Failure")
            }
        }
    }
}

sealed class AddInterestScreenState(
    open val inputError: InputError?
) : ScreenState {

    val shouldHandleBackButton: Boolean
        get() = this !is AddFirstKeywordStep

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
        val addedKeywords: List<String>,
        val interestName: String = addedKeywords.first(),
        override val inputError: InputError? = null,
    ) : AddInterestScreenState(inputError)
}

private fun AddInterestScreenState.AddAnotherKeywordStep.onSkipClicked(): AddInterestScreenState =
    AddInterestScreenState.AddInterestNameStep(addedKeywords = addedKeywords)

private fun AddInterestScreenState.AddFirstKeywordStep.onContinueClicked(): AddInterestScreenState =
    if (currentKeyword.isNotEmpty()) AddInterestScreenState.AddInterestNameStep(addedKeywords = listOf(currentKeyword))
    else this.copy(inputError = InputError(ResString(R.string.error_interest_name_empty)))

private fun AddInterestScreenState.AddAnotherKeywordStep.onContinueClicked(): AddInterestScreenState =
    if (currentKeyword.isNotEmpty())
        AddInterestScreenState.AddInterestNameStep(addedKeywords = addedKeywords.plus(currentKeyword))
    else this.copy(inputError = InputError(ResString(R.string.error_keyword_name_empty)))

private fun AddInterestScreenState.AddAnotherKeywordStep.onAddAnotherKeywordClicked(): AddInterestScreenState =
    if (currentKeyword.isNotEmpty())
        AddInterestScreenState.AddAnotherKeywordStep(
            currentKeyword = "",
            addedKeywords = addedKeywords.plus(currentKeyword)
        )
    else this.copy(inputError = InputError(ResString(R.string.error_keyword_name_empty)))


private fun AddInterestScreenState.AddFirstKeywordStep.onAddAnotherKeywordClicked(): AddInterestScreenState =
    if (currentKeyword.isNotEmpty())
        AddInterestScreenState.AddAnotherKeywordStep(addedKeywords = listOf(currentKeyword))
    else this.copy(inputError = InputError(ResString(R.string.error_keyword_name_empty)))


private fun AddInterestScreenState.AddInterestNameStep.onBackPressed(): AddInterestScreenState =
    if (addedKeywords.size > 1) {
        AddInterestScreenState.AddAnotherKeywordStep(
            currentKeyword = addedKeywords.last(),
            addedKeywords = addedKeywords,
            inputError = null
        )
    } else {
        AddInterestScreenState.AddFirstKeywordStep(
            currentKeyword = addedKeywords.last(),
            inputError = null
        )
    }


private fun AddInterestScreenState.AddAnotherKeywordStep.onBackPressed(): AddInterestScreenState =
    if (addedKeywords.size > 1) {
        val keywordsToKeep = addedKeywords.pop()
        AddInterestScreenState.AddAnotherKeywordStep(
            currentKeyword = addedKeywords.last(),
            addedKeywords = keywordsToKeep,
            inputError = null
        )
    } else {
        AddInterestScreenState.AddFirstKeywordStep(
            currentKeyword = addedKeywords.last(),
            inputError = null
        )
    }
