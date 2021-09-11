package com.danieldisu.hnnotify.common

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class TextValue

data class ResString(@StringRes val resourceId: Int) : TextValue()

data class RawString(val value: String) : TextValue()

@Composable
fun TextValue.asString(): String =
    when (this) {
        is RawString -> this.value
        is ResString -> stringResource(id = this.resourceId)
    }
