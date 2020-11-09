package com.danieldisu.hnnotify.interests

import androidx.lifecycle.ViewModel
import com.danieldisu.hnnotify.common.updateState
import com.danieldisu.hnnotify.data.interests.InterestRepository
import kotlinx.coroutines.flow.MutableStateFlow

class AddInterestViewModel(
    private val interestId: String?,
    private val interestRepository: InterestRepository,
) : ViewModel() {

    val stateFlow = MutableStateFlow(AddInterestsScreenState(interestId))

    init {
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

}


data class AddInterestsScreenState(
    val interestId: String? = null,
    val keywords: List<String> = emptyList(),
)
