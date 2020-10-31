package com.danieldisu.hnnotify.application.di.modules

import com.danieldisu.hnnotify.stories.TopStoriesViewModel
import org.koin.dsl.module

object UiModule {

    operator fun invoke() = module {
        factory { TopStoriesViewModel(get()) }
    }

}
