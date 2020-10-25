package com.danieldisu.hnnotify.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class GetInterestingTopStoriesResponse(
    val stories: List<Story>
)
