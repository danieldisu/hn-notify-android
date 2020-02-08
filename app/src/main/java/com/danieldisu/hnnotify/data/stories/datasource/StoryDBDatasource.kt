package com.danieldisu.hnnotify.data.stories.datasource

import androidx.room.*

@Dao
interface StoryDBDatasource {

  @Query("SELECT * FROM story WHERE id = :id")
  suspend fun findById(id: String): StoryDBO?

  @Insert
  suspend fun insert(storyDBO: StoryDBO)

}

@Entity(tableName = "story")
data class StoryDBO(
  @PrimaryKey val id: String,
  val score: Int,
  val by: String,
  val time: Long,
  val title: String,
  val type: String,
  val url: String?,
  val text: String?,
  val kids: String
)
