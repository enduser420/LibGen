package com.project.libgen.presentation.book_list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.project.libgen.Screen
import com.project.libgen.presentation.book_list.components.BookItem
import com.project.libgen.presentation.book_list.components.FilterSection
import com.project.libgen.presentation.components.util.SnackbarController
import kotlinx.coroutines.launch

@Composable
fun BookListScreen(
    navController: NavController
) {
    ScreenContent(navController)
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun ScreenContent(
    navController: NavController,
    viewModel: BookListViewModel = hiltViewModel()
) {
    val state = viewModel.bookList.value
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val snackbarController = SnackbarController(viewModel.viewModelScope)

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Library Genesis",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )
                },
                elevation = 5.dp,
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                }
            )
        },
        drawerContent = {
            Column {
                TextButton(onClick = { navController.navigate(Screen.BookmarkList.route) }) {
                    Row(
                        Modifier.padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            Icons.Filled.LibraryBooks,
                            modifier = Modifier.padding(end = 5.dp),
                            contentDescription = null
                        )
                        Text("Bookmarks")
                    }
                }
                TextButton(onClick = {
                    viewModel.logout().also {
                        navController.popBackStack()
                        navController.navigate(Screen.UserLogin.route)
                    }
                }) {
                    Row(
                        Modifier.padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            Icons.Filled.Logout,
                            modifier = Modifier.padding(end = 5.dp),
                            contentDescription = null
                        )
                        Text("Sign Out")
                    }
                }
            }
        },
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 5.dp, top = 10.dp, bottom = 10.dp)
                            .weight(1f),
                        value = viewModel.searchQuery.value,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search,
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                viewModel.onSearch()
                            }
                        ),
                        label = {
                            Text(text = "Search")
                        },
                        singleLine = true,
                        onValueChange = { viewModel.onSearchQueryChanged(it) },
                        trailingIcon = {
                            IconButton(onClick = {
                                viewModel.onSearch()
                            }) {
                                Icon(Icons.Filled.Search, contentDescription = null)
                            }
                        }
                    )
                    FilterSection(viewModel)
                }
                LazyColumn(
                    contentPadding = PaddingValues(5.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    state = scrollState
                ) {
                    items(state.bookList) { book ->
                        BookItem(navController, book)
                    }
                }
                if (state.error.isNotBlank()) {
                    snackbarController.getScope().launch {
                        snackbarController.showSnackbar(
                            scaffoldState = scaffoldState,
                            message = state.error
                        )
                    }
                }
                if (state.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                if (viewModel.searched.value and state.bookList.isEmpty() and !state.isLoading) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "No results.",
                        style = MaterialTheme.typography.h5,
                    )
                }
            }
        })
}
