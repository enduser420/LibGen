package com.project.libgen.BookList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.libgen.Screen
import com.project.libgen.data.model.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@Composable
fun BookListScreen(
    navController: NavController
) {
    ScreenContent(navController)
}

@Composable
private fun ScreenContent(
    navController: NavController
) {
    val viewModel = BookListViewModel()
    val scrollState = rememberLazyListState()
    fun onSearchClicked() {
        CoroutineScope(IO).launch {
            viewModel.onSearch(viewModel.searchQuery.value)
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                value = viewModel.searchQuery.value,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search,
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { onSearchClicked() }
                ),
                label = {
                    Text(text = "Search")
                },
                onValueChange = { viewModel.onsearchQueryChange(it) },
                trailingIcon = {
                    IconButton(onClick = {
                        onSearchClicked()
                    }) {
                        Icon(Icons.Filled.Search, contentDescription = null)
                    }
                }
            )
            LazyColumn(
                contentPadding = PaddingValues(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                state = scrollState
            ) {
                items(viewModel.booklist.value) { book ->
                    BookItem(navController, book)
                }
            }
        }
    }
}

@Composable
private fun BookItem(
    navController: NavController,
    Book: Book
) {
    fun onBookClicked() {
        navController.navigate(route = Screen.BookDetials.passId(Book.id))
    }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = { onBookClicked() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp)
            ) {
                Text(
                    text = Book.id,
                    modifier = Modifier.padding(end = 5.dp),
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = Book.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "by ${Book.author}",
                    modifier = Modifier.padding(start = 5.dp).weight(1f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Row {
                    Text(text = "Pages: ${Book.pages}", modifier = Modifier.padding(end = 5.dp))
                    Text(text = Book.extension)
                }
            }
        }
    }
}