package com.danieldisu.hnnotify.presentation.stories.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.danieldisu.hnnotify.databinding.ItemViewStoryBinding
import com.danieldisu.hnnotify.presentation.stories.viewdata.StoryRow


class StoryTitleRowItemView(context: Context) : StoryRowItemView, LinearLayout(context) {

  private val binding =
    ItemViewStoryBinding.inflate(LayoutInflater.from(context), this, true)

  override fun bind(viewModel: StoryRow) {
    val story = (viewModel as StoryRow.StoryTitleRow).story
    binding.titleView.text = story.title
    binding.upvoteCountView.text = story.score.toString()
    binding.commentCountView.text = story.commentCount().toString()

    setOnClickListener {
      if (story.url != null) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(story.url)
        context.startActivity(i)
      }
    }
  }
}
