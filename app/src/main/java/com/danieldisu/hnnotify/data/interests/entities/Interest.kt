package com.danieldisu.hnnotify.data.interests.entities

data class Interest(
  val id: String,
  val name: String,
  val keywords: List<String>,
  val insertedAt: Long
)
