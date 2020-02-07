package com.danieldisu.hnnotify.presentation.stories.views

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.danieldisu.hnnotify.databinding.ItemViewCategoryTitleBinding
import com.danieldisu.hnnotify.presentation.stories.viewdata.StoryRow

class CategoryTitleItemView(context: Context) : StoryRowItemView, LinearLayout(context) {

  private val binding =
    ItemViewCategoryTitleBinding.inflate(LayoutInflater.from(context), this, true)

  override fun bind(viewModel: StoryRow) {
    binding.titleView.text = (viewModel as StoryRow.CategoryTitle).value
  }

}
