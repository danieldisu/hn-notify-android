package com.danieldisu.hnnotify.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Story(
    val id: String,
    val title: String
)
