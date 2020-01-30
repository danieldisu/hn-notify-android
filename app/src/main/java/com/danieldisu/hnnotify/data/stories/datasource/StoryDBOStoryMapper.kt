package com.danieldisu.hnnotify.data.stories.datasource

import com.danieldisu.hnnotify.data.stories.entities.Story

fun StoryDBO.toStory(): Story =
  Story(
    id = this.id,
    by = this.by,
    score = this.score,
    text = this.text,
    time = this.time,
    title = this.title,
    type = this.type,
    url = this.url
  )

fun Story.toDBO(): StoryDBO =
  StoryDBO(
    id = this.storyId.storyId,
    by = this.by,
    score = this.score,
    text = this.text,
    time = this.time,
    title = this.title,
    type = this.type,
    url = this.url
  )
