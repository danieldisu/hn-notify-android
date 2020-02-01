package com.danieldisu.hnnotify.data.interesting.datasource

import androidx.room.*
import com.danieldisu.hnnotify.data.common.StoryId
import com.danieldisu.hnnotify.data.interesting.entity.InterestingStory
import org.threeten.bp.Instant

private const val SEPARATOR = "___"

class InterestingStoriesDbDataSource(
  private val interestingStoriesDao: InterestingStoriesDao
) : InterestingStoriesDataSource {
  override suspend  fun save(interestingStory: InterestingStory) =
    interestingStoriesDao.save(interestingStory.toDbo())

  override suspend fun getAll(): List<InterestingStory> =
    interestingStoriesDao.getAll().map { it.toDomain() }
}

@Dao
interface InterestingStoriesDao {

  @Insert
  suspend fun save(interestingStoryDbo: InterestingStoryDbo)

  @Query("SELECT * FROM interesting_story")
  suspend fun getAll(): List<InterestingStoryDbo>

}

private fun InterestingStory.toDbo(): InterestingStoryDbo {
  return InterestingStoryDbo(
    id = InterestingStoryDboIdGenerator.generate(this),
    insertedAt = foundAt.toEpochMilli(),
    interestingFor = interestingFor.joinToString(SEPARATOR),
    storyId = storyId.storyId
  )
}

private fun InterestingStoryDbo.toDomain(): InterestingStory {
  return InterestingStory(
    storyId = StoryId(storyId),
    interestingFor = interestingFor.split(SEPARATOR),
    foundAt = Instant.ofEpochMilli(insertedAt)
  )
}


@Entity(tableName = "interesting_story")
data class InterestingStoryDbo(
  @PrimaryKey val id: String,
  val storyId: String,
  val interestingFor: String,
  val insertedAt: Long
)
