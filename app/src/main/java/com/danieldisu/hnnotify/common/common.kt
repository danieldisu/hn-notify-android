package com.danieldisu.hnnotify.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
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
    Text(text = "error $error")
}

@Composable
fun showAsDialog(showDialog: Boolean, dialog: @Composable () -> Unit) {
    if (showDialog) {
        dialog()
    }
}

@Composable
fun EmptyContent() {

}

fun noContent(): Any = {}

@Composable
fun HorizontalSpacer(
    size: Int = 16,
) = Spacer(modifier = Modifier.width(size.dp))

@Composable
fun VerticalSpacer(
    size: Int = 16
) = Spacer(modifier = Modifier.height(size.dp))

typealias ComposableUnit = @Composable () -> Unit
