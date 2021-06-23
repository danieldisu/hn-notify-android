package com.danieldisu.hnnotify.data.stories.api

import com.danieldisu.hnnotify.data.stories.Story
import kotlinx.serialization.Serializable

@Serializable
data class GetInterestingTopStoriesResponse(
    val stories: List<Story>
)
