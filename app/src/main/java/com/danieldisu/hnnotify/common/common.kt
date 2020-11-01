package com.danieldisu.hnnotify.common

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingView() =
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(70.dp)
        )
    }

@Composable
fun ErrorView(error: Throwable) {
    Text(text = "error ${error.printStackTrace()}")
}

@Composable
fun showAsDialog(showDialog: Boolean, dialog: @Composable () -> Unit) {
    if (showDialog) {
        dialog()
    }
}
