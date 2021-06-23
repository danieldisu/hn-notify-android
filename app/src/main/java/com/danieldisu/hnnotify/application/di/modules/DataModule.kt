package com.danieldisu.hnnotify.application.di.modules

import com.danieldisu.hnnotify.data.core.ApiServiceBuilder
import com.danieldisu.hnnotify.data.interests.InterestRepository
import com.danieldisu.hnnotify.data.interests.api.InterestApi
import com.danieldisu.hnnotify.data.stories.StoryRepository
import com.danieldisu.hnnotify.data.stories.api.StoryApi
import com.danieldisu.hnnotify.data.user.UserRepository
import org.koin.dsl.module

object DataModule {

    operator fun invoke() = module {
        single { ApiServiceBuilder.build(StoryApi::class) }
        single { ApiServiceBuilder.build(InterestApi::class) }
        factory { StoryRepository(get()) }
        factory { InterestRepository(get()) }
        factory { UserRepository() }
    }

}
