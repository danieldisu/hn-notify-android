package com.danieldisu.hnnotify.interests

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.ui.tooling.preview.Preview
import com.danieldisu.hnnotify.R

@Composable
fun AddInterestScreen(navController: NavHostController, viewModel: AddInterestViewModel) {
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
            Row { AddInterestForm(state.keywords, onInterestNameValueChange, onAddKeywordClick) }
            Spacer(modifier = Modifier.height(16.dp))
            ButtonBar(onConfirmButtonClick, onCancelButtonClick)
        }
    }
}

@Composable
fun AddInterestForm(keywords: List<String>, onValueChange: (String) -> Unit, onAddKeywordClick: () -> Unit) {
    Column {
        Row { Text(text = "Add an interest", style = MaterialTheme.typography.h6) }
        Spacer(modifier = Modifier.height(16.dp))
        Row { AddInterestTextField(onValueChange) }
        Spacer(modifier = Modifier.height(16.dp))
        Row { KeywordsField(onAddKeywordClick) }
        Spacer(modifier = Modifier.height(16.dp))
        KeywordList(keywords)
    }
}

@Composable
fun KeywordList(keywords: List<String>) {
    ScrollableColumn(modifier = Modifier.defaultMinSizeConstraints(minHeight = 32.dp)) {
        keywords.forEach {
            KeywordItemView(it)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun KeywordItemView(keyword: String) {
    Card(elevation = 2.dp) {
        Row(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
            Text(text = keyword)
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                modifier = Modifier.height(24.dp),
                onClick = {},
            ) { Icon(asset = vectorResource(id = R.drawable.ic_edit), modifier = Modifier.size(16.dp)) }
            TextButton(
                modifier = Modifier.height(24.dp),
                onClick = {},
            ) { Icon(asset = vectorResource(id = R.drawable.ic_delete), modifier = Modifier.size(16.dp)) }
        }
    }
}

@Composable
fun RowScope.KeywordsField(onAddKeywordClick: () -> Unit) {
    Row {
        Text(
            text = "Keywords",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(onClick = onAddKeywordClick) {
            Text(text = "Add a new keyword")
        }
    }

}

@Composable
private fun AddInterestTextField(onValueChange: (String) -> Unit) =
    OutlinedTextField(
        value = "",
        label = { Text("Interest Name") },
        onValueChange = onValueChange
    )

@Composable
private fun ColumnScope.ButtonBar(
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
