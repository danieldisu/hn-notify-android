package com.danieldisu.hnnotify.data.interests

sealed class Interest(open val id: String)

data class KeywordInterest(
    override val id: String,
    val name: String,
    val keywords: List<String>,
) : Interest(id)
