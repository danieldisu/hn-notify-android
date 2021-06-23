package com.danieldisu.hnnotify.application.di.modules

import com.danieldisu.hnnotify.domain.GetUserInterestsUseCase
import com.danieldisu.hnnotify.domain.GetUserTopStoriesUseCase
import org.koin.dsl.module
import org.koin.dsl.single

object DomainModule {

    operator fun invoke() = module {
        single<GetUserTopStoriesUseCase>()
        single<GetUserInterestsUseCase>()
    }

}
