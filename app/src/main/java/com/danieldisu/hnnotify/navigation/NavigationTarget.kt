package com.danieldisu.hnnotify.navigation

import com.danieldisu.hnnotify.navigation.NavigationPath.editInterestNoParams

sealed class NavigationTarget(val navigationPath: String) {

    object AddInterest : NavigationTarget(NavigationPath.addInterest)

    object TopStories : NavigationTarget(NavigationPath.topStories)

    object Interests : NavigationTarget(NavigationPath.interests)

    data class EditInterest(
        val interestId: String
    ) : NavigationTarget("$editInterestNoParams?interestId=$interestId")
}
