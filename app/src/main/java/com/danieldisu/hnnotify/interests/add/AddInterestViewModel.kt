package com.danieldisu.hnnotify.interests.add

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.danieldisu.hnnotify.R
import com.danieldisu.hnnotify.common.InputError
import com.danieldisu.hnnotify.common.ResString
import com.danieldisu.hnnotify.common.ScreenState
import com.danieldisu.hnnotify.common.pop
import com.danieldisu.hnnotify.common.update
import com.danieldisu.hnnotify.data.core.ApiError
import com.danieldisu.hnnotify.data.core.getError
import com.danieldisu.hnnotify.data.interests.InterestRepository
import com.danieldisu.hnnotify.interests.add.views.AddInterestNameViewEventListener
import com.danieldisu.hnnotify.interests.add.views.AddKeywordEventListener
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddInterestViewModel(
    private val interestRepository: InterestRepository,
    private val navController: NavController,
) : ViewModel(), AddKeywordEventListener, AddInterestNameViewEventListener {

    val stateFlow: MutableStateFlow<AddInterestScreenState> =
        MutableStateFlow(AddInterestScreenState.AddFirstKeywordStep())

    val currentState: AddInterestScreenState
        get() = stateFlow.value

    override fun onCreateClicked() {
        val state = currentState as AddInterestScreenState.AddInterestNameStep
        if (state.interestName.isEmpty()) {
            state.copy(inputError = InputError(ResString(R.string.error_interest_name_empty))).update(stateFlow)
        } else {
            state.copy(creatingInterest = true).update(stateFlow)
            createInterest(state)
        }
    }

    // Because all steps have an input value we can reuse this function, if this changes we should create a different
    // function for each state
    override fun onCurrentInputValueChanged(value: String) {
        when (val state = stateFlow.value) {
            is AddInterestScreenState.AddFirstKeywordStep -> state.copy(currentKeyword = value)
            is AddInterestScreenState.AddAnotherKeywordStep -> state.copy(currentKeyword = value)
            is AddInterestScreenState.AddInterestNameStep -> state.copy(interestName = value)
        }.update(stateFlow)
    }

    override fun onContinueClicked() {
        when (val state = stateFlow.value) {
            is AddInterestScreenState.AddFirstKeywordStep -> state.onContinueClicked()
            is AddInterestScreenState.AddAnotherKeywordStep -> state.onContinueClicked()
            is AddInterestScreenState.AddInterestNameStep -> throw IllegalStateException()
        }.update(stateFlow)
    }

    override fun onAddMoreClicked() {
        when (val state = stateFlow.value) {
            is AddInterestScreenState.AddFirstKeywordStep -> state.onAddAnotherKeywordClicked()
            is AddInterestScreenState.AddAnotherKeywordStep -> state.onAddAnotherKeywordClicked()
            is AddInterestScreenState.AddInterestNameStep -> throw IllegalStateException()
        }.update(stateFlow)
    }

    override fun onSkipClicked() =
        when (val state = stateFlow.value) {
            is AddInterestScreenState.AddFirstKeywordStep -> throw IllegalStateException()
            is AddInterestScreenState.AddAnotherKeywordStep -> state.onSkipClicked()
            is AddInterestScreenState.AddInterestNameStep -> throw IllegalStateException()
        }.update(stateFlow)

    fun onBackPressed() =
        when (val state = stateFlow.value) {
            is AddInterestScreenState.AddFirstKeywordStep -> throw IllegalStateException()
            is AddInterestScreenState.AddAnotherKeywordStep -> state.onBackPressed()
            is AddInterestScreenState.AddInterestNameStep -> state.onBackPressed()
        }.update(stateFlow)

    fun onRetryClicked() {
        when (val state = stateFlow.value) {
            is AddInterestScreenState.AddInterestNameStep -> {
                state.copy(errorCreatingInterest = false).update(stateFlow)
                onCreateClicked()
            }
            else -> throw IllegalStateException()
        }
    }

    private fun createInterest(state: AddInterestScreenState.AddInterestNameStep) = viewModelScope.launch {
        val result = interestRepository.saveInterest("1", state.interestName, state.addedKeywords)
        when (result) {
            is ApiResult.Success -> onInterestCreatedSuccessfully()
            is ApiResult.Failure -> onInterestCreatingError(state, result.getError())
        }
    }

    private fun onInterestCreatingError(state: AddInterestScreenState.AddInterestNameStep, error: ApiError) {
        Log.e(this::class.simpleName, "Error creating interest", error.cause)
        state.copy(creatingInterest = false, errorCreatingInterest = true).update(stateFlow)
    }

    private fun onInterestCreatedSuccessfully() {
        navController.popBackStack()
    }
}

sealed class AddInterestScreenState(
    open val inputError: InputError?
) : ScreenState {

    val shouldHandleBackButton: Boolean
        get() = this !is AddFirstKeywordStep

    val isLoading: Boolean
        get() = (this is AddInterestNameStep && this.creatingInterest)

    val isError: Boolean
        get() = (this is AddInterestNameStep && this.errorCreatingInterest)

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
        val creatingInterest: Boolean = false,
        val errorCreatingInterest: Boolean = false,
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
