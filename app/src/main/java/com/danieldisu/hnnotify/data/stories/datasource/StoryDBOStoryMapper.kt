package com.danieldisu.hnnotify.data.stories.datasource

import com.danieldisu.hnnotify.data.stories.entities.Story

private const val KID_DELIMITER = ";"

fun StoryDBO.toStory(): Story =
  Story(
    id = this.id,
    by = this.by,
    score = this.score,
    text = this.text,
    time = this.time,
    title = this.title,
    type = this.type,
    url = this.url,
    kids = this.kids.split(KID_DELIMITER)
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
    url = this.url,
    kids = this.kids.joinToString(KID_DELIMITER)
  )
