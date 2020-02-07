package com.danieldisu.hnnotify.infrastructure.localization

import android.content.Context
import androidx.annotation.StringRes

class StringResourceRepository(
  private val context: Context
) {

  fun get(@StringRes stringRes: Int): String = context.getString(stringRes)

}
