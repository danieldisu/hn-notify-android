package com.danieldisu.hnnotify.presentation.stories

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.danieldisu.hnnotify.R
import com.danieldisu.hnnotify.data.stories.entities.Story
import com.danieldisu.hnnotify.databinding.ActivityStoriesBinding
import com.danieldisu.hnnotify.presentation.stories.state.StoriesViewState
import com.danieldisu.hnnotify.presentation.stories.views.StoryItemView
import com.danieldisu.kollectionview.KollectionView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_stories.*
import kotlinx.android.synthetic.main.content_stories.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class StoriesActivity : AppCompatActivity(), StoriesUI {

  private val viewModel: StoriesViewModel by viewModel()
  private lateinit var binding: ActivityStoriesBinding

  private val storiesView: KollectionView<Story, StoryItemView> by lazy {
    @Suppress("UNCHECKED_CAST")
    binding.content.storiesView as KollectionView<Story, StoryItemView>
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityStoriesBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setSupportActionBar(toolbar)
    viewModel.subscribe(this)

    fab.setOnClickListener { view ->
      viewModel.onFabClicked()
    }

    storiesView.configure(
      viewFactory = { StoryItemView(this) }
    )
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_settings -> true
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun update(viewState: StoriesViewState) {
    when (viewState) {
      is StoriesViewState.Loaded -> showLoadedState(viewState)
      is StoriesViewState.Empty -> showEmptyState()
    }
  }

  private fun showEmptyState() {
    storiesView.isGone = true
    emptyStoriesView.isGone = false
  }

  private fun showLoadedState(viewState: StoriesViewState.Loaded) {
    storiesView.isGone = false
    emptyStoriesView.isGone = true
    storiesView.update(viewState.stories)
  }
}
