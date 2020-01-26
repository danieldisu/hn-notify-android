package com.danieldisu.hnnotify.data.interests

import com.danieldisu.hnnotify.data.interests.entities.Interest

interface InterestsRepository {

  fun getInterests(): List<Interest>

}
