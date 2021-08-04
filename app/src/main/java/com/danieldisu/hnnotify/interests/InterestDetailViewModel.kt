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

class InterestDetailViewModel(
    private val interestId: String?,
    private val interestRepository: InterestRepository,
) : ViewModel() {

    val stateFlow = MutableStateFlow(InterestDetailScreenState(interestId = interestId))

    init {
        loadInterestInfo()
    }


    fun onConfirmButtonClick() {

    }

    fun onEditNameClick() {

    }

    fun onEditKeywordClick(keyword: String) {

    }

    fun onDeleteKeywordClick(keyword: String) {

    }

    fun onInterestNameValueChange(newInterestName: String) {

    }

    fun onAddKeywordClick() {
        stateFlow.value = stateFlow.value.copy(isShowingAddKeywordDialog = true)
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
        stateFlow.value = InterestDetailScreenState(
            interestId = interestId,
            keywords = getInterestResult.response.keywords,
            interestName = getInterestResult.response.name
        )
    }

    private fun onGetInterestInfoError(interestResult: ApiResult.Failure<ApiErrorDto>) {
        TODO()
    }
}


data class InterestDetailScreenState(
    val interestId: String? = null,
    val isEdit: Boolean = interestId != null,
    val keywords: List<String> = emptyList(),
    val interestName: String? = null,
    val isShowingAddKeywordDialog: Boolean = false,
)
