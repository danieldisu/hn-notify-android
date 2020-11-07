package com.danieldisu.hnnotify.application.di.modules

import com.danieldisu.hnnotify.interests.AddInterestViewModel
import com.danieldisu.hnnotify.interests.InterestsViewModel
import com.danieldisu.hnnotify.stories.TopStoriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object UiModule {

    operator fun invoke() = module {
        viewModel { TopStoriesViewModel(get()) }
        viewModel { InterestsViewModel(get()) }
        viewModel { (interestId: String) -> AddInterestViewModel(interestId, get()) }
    }

}
