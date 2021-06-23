package com.danieldisu.hnnotify.domain

import com.danieldisu.hnnotify.data.core.logErrors
import com.danieldisu.hnnotify.data.interests.Interest
import com.danieldisu.hnnotify.data.interests.InterestRepository
import com.danieldisu.hnnotify.data.user.UserRepository
import com.danieldisu.hnnotify.domain.core.GenericUseCaseResult
import com.danieldisu.hnnotify.domain.core.toGenericUseCaseResult

class GetUserInterestsUseCase(
    private val userRepository: UserRepository,
    private val interestRepository: InterestRepository,
) {

    suspend operator fun invoke(): GenericUseCaseResult<List<Interest>> =
        userRepository.getCurrentUser()
            .let { user -> interestRepository.getInterests(user.id) }
            .logErrors()
            .toGenericUseCaseResult()
}
