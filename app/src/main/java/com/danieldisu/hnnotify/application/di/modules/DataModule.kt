package com.danieldisu.hnnotify.application.di.modules

import com.danieldisu.hnnotify.data.core.ApiServiceBuilder
import com.danieldisu.hnnotify.data.stories.StoryApi
import com.danieldisu.hnnotify.data.stories.StoryRepository
import org.koin.dsl.module

object DataModule {

    operator fun invoke() = module {
        single { ApiServiceBuilder.build(StoryApi::class) }
        factory { StoryRepository(get()) }
    }

}
