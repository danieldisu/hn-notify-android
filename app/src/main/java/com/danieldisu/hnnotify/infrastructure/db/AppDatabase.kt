package com.danieldisu.hnnotify.infrastructure.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.danieldisu.hnnotify.data.interesting.datasource.InterestingStoriesDao
import com.danieldisu.hnnotify.data.interesting.datasource.InterestingStoryDbo
import com.danieldisu.hnnotify.data.stories.datasource.StoryDBDatasource
import com.danieldisu.hnnotify.data.stories.datasource.StoryDBO

@Database(
  entities = [
    StoryDBO::class,
    InterestingStoryDbo::class
  ],
  version = 1,
  exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

  abstract fun storyDBDatasource(): StoryDBDatasource

  abstract fun interestingStoriesDao(): InterestingStoriesDao

}


object DatabaseHolder {

  fun buildDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
      context,
      AppDatabase::class.java, "hnnotifydb"
    ).build()
  }

}
