package com.danieldisu.hnnotify.interests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danieldisu.hnnotify.data.interests.Interest
import com.danieldisu.hnnotify.data.interests.InterestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class InterestsViewModel(
    private val interestRepository: InterestRepository
) : ViewModel() {

    val stateFlow = MutableStateFlow(InterestsScreenState.Initial)

    init {
        viewModelScope.launch {
            stateFlow.value = InterestsScreenState(
                interests = interestRepository.getInterests(),
                isLoading = false
            )
        }
    }

    fun onActionButtonClick() {
        stateFlow.navigate(InterestsScreenNavState.AddInterest)
    }

    fun onInterestClicked() {

    }

    fun onAddInterestDialogDismiss() {
    }

}

private fun MutableStateFlow<InterestsScreenState>.navigate(newNavState: InterestsScreenNavState) {
    this.value = this.value.copy(navState = newNavState)
}

data class InterestsScreenState(
    val navState: InterestsScreenNavState = InterestsScreenNavState.Interests,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val interests: List<Interest> = emptyList()
) {
    companion object {
        val Initial = InterestsScreenState(
            isLoading = true
        )
    }
}

sealed class InterestsScreenNavState {
    object Interests : InterestsScreenNavState()
    object AddInterest : InterestsScreenNavState()
    data class EditInterest(val interestId: String) : InterestsScreenNavState()
}
