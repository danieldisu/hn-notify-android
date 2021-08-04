package com.danieldisu.hnnotify.interests

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun InterestDetailScreen(viewModel: InterestDetailViewModel) {
    val state = viewModel.stateFlow.collectAsState()

    if (state.value.isShowingAddKeywordDialog) {
        AddKeywordDialog(
            onConfirmButtonClick = {},
            onDismiss = {},
        )
    }

    InterestDetailScaffold(
        state = state.value,
        onInterestNameValueChange = viewModel::onInterestNameValueChange,
        onAddKeywordClick = viewModel::onAddKeywordClick,
        onEditNameClick = viewModel::onEditNameClick,
        onEditKeywordClick = viewModel::onEditKeywordClick,
        onDeleteKeywordClick = viewModel::onDeleteKeywordClick,
        onConfirmClick = viewModel::onConfirmButtonClick,
    )
}

@Composable
fun InterestDetailScaffold(
    state: InterestDetailScreenState,
    onInterestNameValueChange: (String) -> Unit,
    onAddKeywordClick: () -> Unit,
    onEditNameClick: () -> Unit,
    onEditKeywordClick: (keyword: String) -> Unit,
    onDeleteKeywordClick: (keyword: String) -> Unit,
    onConfirmClick: () -> Unit,
) {
    Surface(
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Column {
                TitleRow(state.isEdit, onConfirmClick)
                VerticalSpacer()
                Row { InterestName(state.interestName, onEditNameClick) }
                VerticalSpacer()
                Row { InterestTitleRow(onAddKeywordClick) }
                VerticalSpacer()
                InterestKeywordList(state.keywords, onEditKeywordClick, onDeleteKeywordClick)
            }
        }
    }
}

@Composable
private fun TitleRow(isEdit: Boolean, onConfirmClick: () -> Unit) {
    Row {
        Text(text = getTitle(isEdit), style = MaterialTheme.typography.h4)
        Spacer(Modifier.weight(1f))
        ConfirmButton(onConfirmClick)
    }
}

@Composable
private fun getTitle(isEdit: Boolean) =
    if (isEdit) "Edit interest"
    else "Add an interest"


@Composable
private fun InterestName(
    interestName: String?,
    onEditNameClick: () -> Unit,
) =
    Column {
        Text(
            text = "Interest Name",
            style = MaterialTheme.typography.h6
        )
        VerticalSpacer()
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = interestName ?: "Choose a name",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1f))
            EditButton(onEditNameClick)
        }
    }

@Composable
fun InterestKeywordList(
    keywords: List<String>,
    onEditKeywordClick: (keyword: String) -> Unit,
    onDeleteKeywordClick: (keyword: String) -> Unit,
) {
    LazyColumn(modifier = Modifier.defaultMinSize(minHeight = 32.dp)) {
        items(items = keywords) {
            KeywordItemView(it, onEditKeywordClick, onDeleteKeywordClick)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun KeywordItemView(
    keyword: String,
    onEditKeywordClick: (keyword: String) -> Unit,
    onDeleteKeywordClick: (keyword: String) -> Unit,
) {
    Card(elevation = 2.dp) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = keyword,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1f))
            EditButton(onClick = { onEditKeywordClick(keyword) })
            DeleteButton(onClick = { onDeleteKeywordClick(keyword) })
        }
    }
}

@Composable
fun InterestTitleRow(onAddKeywordClick: () -> Unit) {
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

@Preview(showBackground = true)
@Composable
fun InterestDetailScreenPreview() {
    MaterialTheme {
        val state = InterestDetailScreenState(
            interestName = "Kotlin",
            isEdit = true,
            keywords = listOf(
                "JVM",
                "Kotlin",
                "Java",
                "JDK",
                "Scala"
            )
        )
        InterestDetailScaffold(state, {}, {}, {}, {}, {}, {})
    }
}


@Composable
private fun EditButton(onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_edit),
            modifier = Modifier.size(18.dp),
            contentDescription = "Edit"
        )
    }
}

@Composable
private fun DeleteButton(onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_delete),
            modifier = Modifier.size(18.dp),
            contentDescription = "Delete"
        )
    }
}


@Composable
private fun ConfirmButton(onClick: () -> Unit) {
    TextButton(
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_check),
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.CenterVertically),
            contentDescription = "Delete"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun KeywordItemViewPreview() {
    MaterialTheme {
        KeywordItemView(keyword = "Kotlin", {}, {})
    }
}

@Preview(showBackground = true)
@Composable
fun InterestNamePreview() {
    MaterialTheme {
        InterestName(interestName = "Kotlin", onEditNameClick = {})
    }
}
