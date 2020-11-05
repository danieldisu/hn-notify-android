package com.danieldisu.hnnotify.interests

import androidx.lifecycle.ViewModel
import com.danieldisu.hnnotify.data.interests.InterestRepository
import kotlinx.coroutines.flow.MutableStateFlow

class AddInterestViewModel(
    private val interestRepository: InterestRepository
) : ViewModel() {

    val stateFlow = MutableStateFlow(AddInterestsScreenState())

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
    val keywords: List<String> = emptyList()
)