package com.danieldisu.hnnotify.data.interests

sealed class Interest

data class KeywordInterest(
    val id: String,
    val name: String,
    val keywords: List<String>,
)
