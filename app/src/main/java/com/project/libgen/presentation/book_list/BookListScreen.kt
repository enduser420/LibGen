package com.project.libgen.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.project.libgen.Screen
import com.project.libgen.data.model.Book
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
    navController: NavController,
    viewModel: BookListViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberLazyListState()

    fun onSearchClicked() {
        println(viewModel.filterOptions[viewModel.filterIndex.value])
        viewModel.viewModelScope.launch(IO) {
            viewModel.onSearch(viewModel.searchQuery.value, viewModel.filterOptions[viewModel.filterIndex.value])
        }
    }

    fun onFilterClicked() {
        viewModel.filterVisible.value = !viewModel.filterVisible.value
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .weight(.9f),
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
                    singleLine = true,
                    onValueChange = { viewModel.onSearchQueryChanged(it) },
                    trailingIcon = {
                        IconButton(onClick = {
                            onSearchClicked()
                        }) {
                            Icon(Icons.Filled.Search, contentDescription = null)
                        }
                    }
                )
                FilterSection()
            }
//            AnimatedVisibility(
//                visible = viewModel.filterVisible.value, enter = fadeIn() + slideInVertically(),
//                exit = fadeOut() + slideOutVertically()
//            ) {
//                FilterSection()
//            }

            LazyColumn(
                contentPadding = PaddingValues(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                state = scrollState
            ) {
                items(viewModel.bookList.value) { book ->
                    BookItem(navController, book)
                }
            }
        }
    }
}

/*    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .weight(.1f),
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
                    singleLine = true,
                    onValueChange = { viewModel.onSearchQueryChanged(it) },
                    trailingIcon = {
                        IconButton(onClick = {
                            onSearchClicked()
                        }) {
                            Icon(Icons.Filled.Search, contentDescription = null)
                        }
                    }
                )
                FilterSection(filterText = viewModel.filterText)
            }
        }

    }

}


@Composable
fun FilterSection() {
    var filterShow by remember { mutableStateOf(false) }
    val suggestions = listOf("Kotlin", "Java", "Dart", "Python")
    var selectedText by remember { mutableStateOf("") }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (filterShow)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            label = { Text("Label") },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { filterShow = !filterShow })
            }
        )

        DropdownMenu(
            expanded = filterShow,
            onDismissRequest = { filterShow = !filterShow },
            modifier = Modifier.width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            suggestions.forEach {
                DropdownMenuItem(onClick = { filterShow = false }) {
                    Text(it)
                }
            }

        }
    }
}*/

@Composable
private fun FilterSection(
    viewModel: BookListViewModel = hiltViewModel()
) {
    var filterShow by remember { mutableStateOf(false) }

    Box(Modifier.padding(10.dp)) {
        IconButton(onClick = { filterShow = true }) {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
        }

        DropdownMenu(expanded = filterShow, onDismissRequest = { filterShow = !filterShow }) {
            viewModel.filterText.forEachIndexed { i, s ->
                DropdownMenuItem(
                    onClick = {
                        filterShow = false
                        viewModel.filterIndex.value = i
                    },
                    modifier = Modifier.background(
                        if (i == viewModel.filterIndex.value) Color.LightGray else Color.Transparent
                    )
                ) {
                    Text(s)
                }
            }
        }
    }
}

@Composable
private fun BookItem(
    navController: NavController,
    book: Book
) {
    fun onBookClicked() {
        navController.navigate(route = Screen.BookDetails.passId(book.id))
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
                    text = book.id,
                    modifier = Modifier.padding(end = 5.dp),
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.title,
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
                    text = "by ${book.author}",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .weight(1f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Row {
                    Text(text = "Pages: ${book.pages}", modifier = Modifier.padding(end = 5.dp))
                    Text(text = book.extension)
                }
            }
        }
    }
}
