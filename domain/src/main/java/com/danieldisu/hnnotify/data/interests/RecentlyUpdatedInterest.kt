package com.danieldisu.hnnotify.data.interests

sealed class RecentlyUpdatedInterest(open val id: String)

data class RecentlyUpdatedKeywordInterest(
    override val id: String,
    val name: String?,
    val keywords: List<String>
) : RecentlyUpdatedInterest(id)
