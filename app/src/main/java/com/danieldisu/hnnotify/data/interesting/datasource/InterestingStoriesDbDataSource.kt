package com.danieldisu.hnnotify.data.interesting.datasource

import androidx.room.*
import com.danieldisu.hnnotify.data.common.StoryId
import com.danieldisu.hnnotify.data.interesting.entity.InterestingStory
import org.threeten.bp.Instant

@Dao
interface InterestingStoriesDbDataSource : InterestingStoriesDataSource {

  override fun save(interestingStory: InterestingStory) =
    saveInternal(interestingStory.toDbo())

  @Insert
  fun saveInternal(interestingStoryDbo: InterestingStoryDbo)

  override fun getAll(): List<InterestingStory> =
    getAllInternal().map { it.toDomain() }

  @Query("SELECT * FROM interesting_story")
  fun getAllInternal(): List<InterestingStoryDbo>
}

private fun InterestingStory.toDbo(): InterestingStoryDbo =
  InterestingStoryDbo(
    id = InterestingStoryDboIdGenerator.generate(this),
    insertedAt = this.foundAt.toEpochMilli(),
    interestingFor = this.interestingFor,
    storyId = this.storyId.storyId
  )

private fun InterestingStoryDbo.toDomain(): InterestingStory {
  return InterestingStory(
    storyId = StoryId(storyId),
    interestingFor = interestingFor,
    foundAt = Instant.ofEpochMilli(insertedAt)
  )
}


@Entity(tableName = "interesting_story")
data class InterestingStoryDbo(
  @PrimaryKey val id: String,
  val storyId: String,
  val interestingFor: List<String>,
  val insertedAt: Long
)
