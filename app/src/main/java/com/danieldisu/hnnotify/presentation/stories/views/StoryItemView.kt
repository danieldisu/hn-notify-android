package com.danieldisu.hnnotify.presentation.stories.views

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import com.danieldisu.hnnotify.data.stories.entities.Story
import com.danieldisu.kollectionview.internal.KollectionItemView

class StoryItemView(context: Context) : KollectionItemView<Story>, LinearLayout(context) {

  override fun bind(viewModel: Story) {
    val textView = TextView(context)
    textView.text = viewModel.title
    addView(textView)
  }
}
