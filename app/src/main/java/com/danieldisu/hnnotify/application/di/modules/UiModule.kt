package com.danieldisu.hnnotify.application.di.modules

import com.danieldisu.hnnotify.interests.InterestDetailViewModel
import com.danieldisu.hnnotify.interests.InterestsViewModel
import com.danieldisu.hnnotify.stories.TopStoriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object UiModule {

    operator fun invoke() = module {
        viewModel { TopStoriesViewModel(get()) }
        viewModel { InterestsViewModel(get()) }
        viewModel { InterestDetailViewModel(it[0], get()) }
    }

}
