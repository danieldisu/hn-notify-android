package com.danieldisu.hnnotify.interests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danieldisu.hnnotify.data.interests.Interest
import com.danieldisu.hnnotify.domain.GetUserInterestsUseCase
import com.danieldisu.hnnotify.domain.core.GenericUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class InterestsViewModel(
    private val getUserInterestsUseCase: GetUserInterestsUseCase
) : ViewModel() {

    val stateFlow = MutableStateFlow(InterestsScreenState())

    init {
        viewModelScope.launch {
            when (val result = getUserInterestsUseCase()) {
                is GenericUseCaseResult.Error -> onGetUserInterestsFailure(result.value)
                is GenericUseCaseResult.Success -> onGetUserInterestsSuccess(result.value)
            }
        }
    }

    fun onActionButtonClick() {
    }

    fun onInterestClicked() {

    }

    fun onAddInterestDialogDismiss() {
    }

    private fun onGetUserInterestsFailure(value: Throwable) {
        updateState(
            InterestsScreenState(
                isLoading = false,
                error = value
            )
        )
    }

    private fun onGetUserInterestsSuccess(interests: List<Interest>) {
        updateState(
            InterestsScreenState(
                interests = interests,
                isLoading = false
            )
        )
    }

    private fun updateState(newState: InterestsScreenState) {
        stateFlow.value = newState
    }

}

data class InterestsScreenState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val interests: List<Interest> = emptyList()
) {

    val noInterestsLoaded: Boolean = !isLoading && error != null && interests.isEmpty()
}
