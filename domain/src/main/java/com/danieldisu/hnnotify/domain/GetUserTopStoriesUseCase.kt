package com.danieldisu.hnnotify.domain

import com.danieldisu.hnnotify.data.core.logErrors
import com.danieldisu.hnnotify.data.stories.Story
import com.danieldisu.hnnotify.data.stories.StoryRepository
import com.danieldisu.hnnotify.data.user.UserRepository
import com.danieldisu.hnnotify.domain.core.GenericUseCaseResult
import com.danieldisu.hnnotify.domain.core.toGenericUseCaseResult

class GetUserTopStoriesUseCase(
    private val storyRepository: StoryRepository,
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(): GenericUseCaseResult<List<Story>> =
        userRepository.getCurrentUser()
            .let { user -> storyRepository.getInterestingTopStories(user.id) }
            .logErrors()
            .toGenericUseCaseResult()

}
