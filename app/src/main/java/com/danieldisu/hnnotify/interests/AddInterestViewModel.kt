package com.danieldisu.hnnotify.interests

import androidx.lifecycle.ViewModel
import com.danieldisu.hnnotify.data.interests.InterestRepository
import kotlinx.coroutines.flow.MutableStateFlow

class AddInterestViewModel(
    private val interestId: String?,
    private val interestRepository: InterestRepository
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

}

data class AddInterestsScreenState(
    val interestId: String? = null,
    val keywords: List<String> = emptyList(),
)
