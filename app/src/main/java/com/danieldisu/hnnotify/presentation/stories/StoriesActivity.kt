package com.danieldisu.hnnotify.presentation.stories

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.danieldisu.hnnotify.R
import com.danieldisu.hnnotify.presentation.stories.state.StoriesViewState
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_stories.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class StoriesActivity : AppCompatActivity(), StoriesUI {

  private val viewModel: StoriesViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_stories)
    setSupportActionBar(toolbar)
    viewModel.subscribe(this)

    fab.setOnClickListener { view ->
      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show()
    }
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
    println(viewState)
  }
}
