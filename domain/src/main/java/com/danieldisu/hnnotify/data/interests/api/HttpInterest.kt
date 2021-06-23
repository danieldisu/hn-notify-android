package com.danieldisu.hnnotify.data.interests.api

import kotlinx.serialization.Serializable

@Serializable
data class HttpInterest(
    val interestName: String,
    val interestKeywords: List<String>,
)
