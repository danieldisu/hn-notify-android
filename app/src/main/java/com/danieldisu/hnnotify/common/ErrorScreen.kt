package com.danieldisu.hnnotify.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danieldisu.hnnotify.R

@Composable
fun ErrorScreen(
    errorMessage: String,
    onRetryClick: () -> Unit
) {
    Column(
        Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(Modifier.padding(vertical = 16.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_error),
                tint = MaterialTheme.colors.primary,
                contentDescription = "Error icon",
                modifier = Modifier.size(64.dp)
            )
        }
        Row(modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)) {
            Text(text = errorMessage, textAlign = TextAlign.Center, fontSize = 18.sp)
        }
        Row(modifier = Modifier.padding(vertical = 16.dp)) {
            Button(onClick = onRetryClick, modifier = Modifier.defaultMinSize(minWidth = 150.dp)) {
                Text(text = "Retry")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(errorMessage = "This is the error message that will be displayed to the user", {})
}
