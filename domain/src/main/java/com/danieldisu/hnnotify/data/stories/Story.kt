package com.danieldisu.hnnotify.data.stories

import kotlinx.serialization.Serializable

@Serializable
data class Story(
    val id: String,
    val title: String
)
