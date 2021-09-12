package com.danieldisu.hnnotify.common.feedback

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danieldisu.hnnotify.R
import com.danieldisu.hnnotify.common.HorizontalSpacer
import com.danieldisu.hnnotify.common.ResString
import com.danieldisu.hnnotify.common.TextValue
import com.danieldisu.hnnotify.common.asString
import com.danieldisu.hnnotify.common.noContent

@Composable
fun FeedbackLayout(
    state: FeedbackLayoutState,
    onRetryClicked: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    FeedbackLayoutInternal(content, state, onRetryClicked)
}

@Composable
fun FeedbackLayout(
    isError: Boolean = false,
    isLoading: Boolean = false,
    isEmpty: Boolean = false,
    onRetryClicked: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    val state = when {
        isEmpty -> FeedbackLayoutState.Empty()
        isError -> FeedbackLayoutState.Error()
        isLoading -> FeedbackLayoutState.Loading()
        else -> FeedbackLayoutState.Success
    }
    FeedbackLayoutInternal(content, state, onRetryClicked)
}

@Composable
private fun FeedbackLayoutInternal(
    content: @Composable() (BoxScope.() -> Unit),
    state: FeedbackLayoutState,
    onRetryClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        content()

        if (state.transparentLayer) {
            Surface(
                color = Color.Black.copy(alpha = 0.8f),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {}
        }

        when (state) {
            is FeedbackLayoutState.Empty -> EmptyLayout(state.message, onRetryClicked)
            is FeedbackLayoutState.Error -> ErrorLayout(state.message, onRetryClicked)
            is FeedbackLayoutState.Loading -> LoadingLayout(state.message)
            FeedbackLayoutState.Success -> noContent()
        }

    }
}

@Composable
private fun BoxScope.LoadingLayout(message: TextValue) {
    Surface(
        shape = AbsoluteRoundedCornerShape(36.dp),
        color = MaterialTheme.colors.primary,
        elevation = 12.dp,
        contentColor = MaterialTheme.colors.onPrimary,
        modifier = Modifier
            .align(BiasAlignment(0f, 0.7f)),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.size(16.dp)
            )
            HorizontalSpacer()
            Text(
                text = message.asString(),
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}

@Composable
private fun BoxScope.ErrorLayout(message: TextValue, onRetryClicked: () -> Unit) {
    Surface(
        shape = AbsoluteRoundedCornerShape(36.dp),
        color = MaterialTheme.colors.error,
        elevation = 12.dp,
        contentColor = MaterialTheme.colors.onError,
        modifier = Modifier
            .align(BiasAlignment(0f, 0.7f)),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onRetryClicked() }
                .padding(vertical = 16.dp, horizontal = 16.dp)
        ) {
            Icon(Icons.Outlined.Warning, contentDescription = "Error Icon")
            HorizontalSpacer()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text(
                    text = message.asString(),
                    style = MaterialTheme.typography.subtitle2,
                )
                Text(
                    text = stringResource(id = R.string.label_click_to_retry),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun BoxScope.EmptyLayout(message: TextValue, onRetryClicked: () -> Unit) {
    Surface(
        shape = AbsoluteRoundedCornerShape(36.dp),
        color = MaterialTheme.colors.secondary,
        elevation = 12.dp,
        contentColor = MaterialTheme.colors.onSecondary,
        modifier = Modifier
            .align(BiasAlignment(0f, 0.7f)),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onRetryClicked() }
                .padding(vertical = 16.dp, horizontal = 32f.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = message.asString(),
                    style = MaterialTheme.typography.subtitle2,
                )
                Text(
                    text = stringResource(id = R.string.label_click_to_retry),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}


sealed class FeedbackLayoutState(
    val transparentLayer: Boolean = false
) {
    data class Loading(
        val message: TextValue = ResString(R.string.label_loading),
        val showTransparentLayer: Boolean = false,
    ) : FeedbackLayoutState(transparentLayer = showTransparentLayer)

    data class Error(val message: TextValue = ResString(R.string.label_error)) : FeedbackLayoutState()

    data class Empty(val message: TextValue = ResString(R.string.label_empty)) : FeedbackLayoutState()

    object Success : FeedbackLayoutState()
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    FeedbackLayout(state = FeedbackLayoutState.Loading(), {}) {
        Text(text = "this is the background composable", modifier = Modifier.align(Alignment.Center))
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorPreview() {
    FeedbackLayout(state = FeedbackLayoutState.Error(), {}) {
        Text(text = "this is the background composable", modifier = Modifier.align(Alignment.Center))
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyPreview() {
    FeedbackLayout(state = FeedbackLayoutState.Empty(), {}) {
        Text(text = "this is the background composable", modifier = Modifier.align(Alignment.Center))
    }
}
