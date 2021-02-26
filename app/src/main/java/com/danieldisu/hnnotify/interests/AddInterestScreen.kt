package com.danieldisu.hnnotify.interests

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danieldisu.hnnotify.R
import com.danieldisu.hnnotify.common.VerticalSpacer

@Composable
fun AddInterestScreen(viewModel: AddInterestViewModel) {
    val state = viewModel.stateFlow.collectAsState()

    val addKeywordDialog = AddKeywordDialog(
        onConfirmButtonClick = {
            viewModel.onAddKeywordDialogSubmit(it)
        }
    )

    AddInterestScaffold(
        state = state.value,
        onConfirmButtonClick = viewModel::onConfirmButtonClick,
        onCancelButtonClick = viewModel::onCancelButtonClick,
        onInterestNameValueChange = viewModel::onInterestNameValueChange,
        onAddKeywordClick = {
            addKeywordDialog.show()
        }
    )
}

@Composable
fun AddInterestScaffold(
    state: AddInterestsScreenState,
    onConfirmButtonClick: () -> Unit,
    onCancelButtonClick: () -> Unit,
    onInterestNameValueChange: (String) -> Unit,
    onAddKeywordClick: () -> Unit,
) {
    Surface(
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row { AddInterestForm(state.keywords, onAddKeywordClick) }
            VerticalSpacer()
            ButtonBar(onConfirmButtonClick, onCancelButtonClick)
        }
    }
}

@Composable
fun AddInterestForm(keywords: List<String>, onAddKeywordClick: () -> Unit) {
    Column {
        Row { Text(text = "Add an interest", style = MaterialTheme.typography.h4) }
        VerticalSpacer()
        Row { AddInterestTextField() }
        VerticalSpacer()
        Row { KeywordsField(onAddKeywordClick) }
        VerticalSpacer()
        KeywordList(keywords)
    }
}


@Composable
private fun AddInterestTextField() =
    Column {
        Text(
            text = "Interest Name",
            style = MaterialTheme.typography.h6
        )
        VerticalSpacer()
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            Text(text = "Interest1")
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                modifier = Modifier.height(24.dp),
                onClick = {},
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    modifier = Modifier.size(16.dp),
                    contentDescription = "Edit"
                )
            }
        }
    }


@Composable
fun KeywordList(keywords: List<String>) {
    LazyColumn(modifier = Modifier.defaultMinSize(minHeight = 32.dp)) {
        items(items = keywords) {
            keywords.forEach {
                KeywordItemView(it)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun KeywordItemView(keyword: String) {
    Card(elevation = 2.dp) {
        Row(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()) {
            Text(text = keyword)
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                modifier = Modifier.height(24.dp),
                onClick = {},
            ) { Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                modifier = Modifier.size(16.dp),
                contentDescription = "Edit"
            ) }
            TextButton(
                modifier = Modifier.height(24.dp),
                onClick = {},
            ) { Icon(
                painter = painterResource(id = R.drawable.ic_delete),
                modifier = Modifier.size(16.dp),
                contentDescription = "Delete"
            ) }
        }
    }
}

@Composable
fun KeywordsField(onAddKeywordClick: () -> Unit) {
    Row {
        Text(
            text = "Keywords",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(onClick = onAddKeywordClick) {
            Text(text = "Add a new keyword")
        }
    }

}

@Composable
private fun ButtonBar(
    onConfirmButtonClick: () -> Unit,
    onCancelButtonClick: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        CancelButton(onCancelButtonClick)
        Spacer(modifier = Modifier.width(10.dp))
        CreateButton(onConfirmButtonClick)
    }
}

@Composable
private fun RowScope.CreateButton(onConfirmButtonClick: () -> Unit) =
    Button(
        modifier = Modifier.weight(1f),
        onClick = onConfirmButtonClick,
        content = { Text("Create") }
    )

@Composable
private fun RowScope.CancelButton(onCancelButtonClick: () -> Unit) =
    Button(
        modifier = Modifier.weight(1f),
        onClick = onCancelButtonClick,
        content = { Text("Cancel") }
    )

@Preview(showBackground = true)
@Composable
fun AddInterestScreenPreview() {
    MaterialTheme {
        val state = AddInterestsScreenState(
            keywords = listOf(
                "JVM",
                "Kotlin",
                "Java",
                "JDK",
                "Scala"
            )
        )
        AddInterestScaffold(state, {}, {}, {}, {})
    }
}

@Preview(showBackground = true)
@Composable
fun KeywordItemViewPreview() {
    MaterialTheme {
        KeywordItemView(keyword = "Kotlin")
    }
}
