package com.danieldisu.hnnotify.presentation.stories.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.danieldisu.hnnotify.data.stories.entities.Story
import com.danieldisu.hnnotify.databinding.ItemViewStoryBinding
import com.danieldisu.kollectionview.internal.KollectionItemView


class StoryItemView(context: Context) : KollectionItemView<Story>, LinearLayout(context) {

  private val binding =
    ItemViewStoryBinding.inflate(LayoutInflater.from(context), this, true)

  override fun bind(viewModel: Story) {
    binding.titleView.text = viewModel.title

    setOnClickListener {
      if (viewModel.url != null) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(viewModel.url)
        context.startActivity(i)
      }
    }
  }
}
