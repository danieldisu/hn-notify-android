package com.danieldisu.hnnotify.infrastructure.di

import android.content.Context
import com.danieldisu.hnnotify.data.interests.InterestsRepository
import com.danieldisu.hnnotify.data.interests.impl.InMemoryInterestsRepository
import com.danieldisu.hnnotify.data.stories.StoryRepository
import com.danieldisu.hnnotify.data.stories.impl.StoryRepositoryImpl
import com.danieldisu.hnnotify.data.stories.impl.StoryService
import com.danieldisu.hnnotify.data.top.TopStoriesRepository
import com.danieldisu.hnnotify.data.top.impl.TopStoriesRepositoryImpl
import com.danieldisu.hnnotify.data.top.impl.TopStoriesService
import com.danieldisu.hnnotify.domain.fetch.FetchTopStoriesUseCase
import com.danieldisu.hnnotify.domain.scan.InterestMatcher
import com.danieldisu.hnnotify.domain.scan.ScanInterestingStoriesUseCase
import com.danieldisu.hnnotify.infrastructure.db.AppDatabase
import com.danieldisu.hnnotify.infrastructure.db.DatabaseHolder
import com.danieldisu.hnnotify.infrastructure.network.OkHttpClientBuilder
import com.danieldisu.hnnotify.infrastructure.network.RetrofitHNServiceBuilder
import com.danieldisu.hnnotify.presentation.stories.StoriesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module

object ApplicationDependencyContainer {

  fun init(androidContext: Context): KoinApplication {
    return startKoin {
      // use AndroidLogger as Koin Logger - default Level.INFO
      androidLogger()

      // use the Android context given there
      androidContext(androidContext)

      // load properties from assets/koin.properties file
      androidFileProperties()

      // module list
      modules(Modules.get())
    }
  }

}


private object Modules {

  private val data = module {
    single { OkHttpClientBuilder() }
    single { RetrofitHNServiceBuilder(get()) }
    single<StoryService> { buildRetrofitService() }
    single<TopStoriesService> { buildRetrofitService() }

    factory<InterestsRepository> { InMemoryInterestsRepository() }
    factory<StoryRepository> { StoryRepositoryImpl(get(), get()) }
    factory<TopStoriesRepository> { TopStoriesRepositoryImpl(get()) }
  }

  private val domain = module {
    factory { FetchTopStoriesUseCase(get(), get()) }
    factory { ScanInterestingStoriesUseCase(get(), get(), get()) }
    factory { InterestMatcher() }
  }

  private val presentation = module {
    factory { StoriesViewModel(get()) }
  }

  private val infra = module {
    single { DatabaseHolder.buildDatabase(get()) }
    single { get<AppDatabase>().storyDBDatasource() }
  }

  private inline fun <reified T> Scope.buildRetrofitService(): T {
    return get<RetrofitHNServiceBuilder>().build(T::class.java)
  }

  fun get(): List<Module> = listOf(
    data,
    domain,
    presentation,
    infra
  )

}
