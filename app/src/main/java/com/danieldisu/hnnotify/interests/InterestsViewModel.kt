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
        stateFlow.update(isShowingAddInterestDialog = true)
    }

    fun onInterestClicked() {

    }

    fun onAddInterestDialogDismiss() {
        stateFlow.update(isShowingAddInterestDialog = false)
    }

    fun onConfirmInterestDialog(interestDialogState: InterestDialogState) {
        stateFlow.update(isShowingAddInterestDialog = false)
        println(interestDialogState)
    }
}

private fun MutableStateFlow<InterestsScreenState>.update(
    isShowingAddInterestDialog: Boolean?
) {
    value = value.copy(
        isShowingAddInterestDialog = isShowingAddInterestDialog ?: value.isShowingAddInterestDialog
    )
}

data class InterestsScreenState(
    val isLoading: Boolean = false,
    val isShowingAddInterestDialog: Boolean = false,
    val error: Throwable? = null,
    val interests: List<Interest> = emptyList()
) {
    companion object {
        val Initial = InterestsScreenState(
            isLoading = true
        )
    }
}
