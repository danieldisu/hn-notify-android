package com.danieldisu.hnnotify.navigation

sealed class FirstLevelScreen(val route: String) {
    object TopStories : FirstLevelScreen("topStories")
    object Interests : FirstLevelScreen("interests")

    companion object {
        fun from(route: String?): FirstLevelScreen? =
            when (route) {
                "topStories" -> TopStories
                "interests" -> Interests
                else -> null
            }
    }
}
