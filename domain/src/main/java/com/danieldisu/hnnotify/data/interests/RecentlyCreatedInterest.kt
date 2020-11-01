package com.danieldisu.hnnotify.data.interests

sealed class RecentlyCreatedInterest

data class RecentlyCreatedKeywordInterest(
    val name: String?,
    val keywords: List<String>
): RecentlyCreatedInterest()
