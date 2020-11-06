package com.danieldisu.hnnotify.application.di.modules

import com.danieldisu.hnnotify.interests.AddInterestViewModel
import com.danieldisu.hnnotify.interests.InterestsViewModel
import com.danieldisu.hnnotify.main.MainViewModel
import com.danieldisu.hnnotify.stories.TopStoriesViewModel
import org.koin.dsl.module

object UiModule {

    operator fun invoke() = module {
        factory { TopStoriesViewModel(get()) }
        factory { MainViewModel() }
        factory { InterestsViewModel(get()) }
        factory { (interestId: String?) -> AddInterestViewModel(interestId, get()) }
    }

}
