package com.danieldisu.hnnotify.interests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danieldisu.hnnotify.common.updateState
import com.danieldisu.hnnotify.data.core.ApiErrorDto
import com.danieldisu.hnnotify.data.interests.Interest
import com.danieldisu.hnnotify.data.interests.InterestRepository
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddInterestViewModel(
    private val interestId: String?,
    private val interestRepository: InterestRepository,
) : ViewModel() {

    val stateFlow = MutableStateFlow(AddInterestsScreenState(interestId))

    init {
        loadInterestInfo()
    }


    fun onConfirmButtonClick() {

    }

    fun onCancelButtonClick() {

    }

    fun onInterestNameValueChange(newInterestName: String) {

    }

    fun onAddKeywordDialogSubmit(newKeyword: String) {
        stateFlow.updateState {
            it.copy(keywords = it.keywords.plus(newKeyword))
        }
    }

    private fun loadInterestInfo() = viewModelScope.launch {
        if (interestId != null) {
            when (val getInterestResult = interestRepository.getInterest("1", interestId)) {
                is ApiResult.Success -> onGetInterestInfoSuccess(getInterestResult)
                is ApiResult.Failure -> onGetInterestInfoError(getInterestResult)
            }
        }
    }

    private fun onGetInterestInfoSuccess(getInterestResult: ApiResult.Success<Interest>) {
        stateFlow.value = AddInterestsScreenState(
            interestId = interestId,
            keywords = getInterestResult.response.keywords
        )
    }

    private fun onGetInterestInfoError(interestResult: ApiResult.Failure<ApiErrorDto>) {
        TODO()
    }
}


data class AddInterestsScreenState(
    val interestId: String? = null,
    val keywords: List<String> = emptyList(),
)
