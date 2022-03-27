package com.project.libgen.presentation.book_list

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.project.libgen.Screen
import com.project.libgen.presentation.book_list.components.BookItem
import com.project.libgen.presentation.book_list.components.FilterSection
import com.project.libgen.presentation.components.util.Mode
import com.project.libgen.presentation.components.util.SnackbarController
import kotlinx.coroutines.CoroutineScope
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
    val mode by viewModel.modeState.observeAsState(Mode.NONFICTION)
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
                },
                actions = {
                    when (mode) {
                        Mode.NONFICTION -> {
                            TextButton(onClick = viewModel::toggleSection) {
                                Text(
                                    text = "SCI-TECH",
                                    style = MaterialTheme.typography.body1,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colors.primary
                                )
                            }
                        }
                        Mode.FICTION -> {
                            TextButton(onClick = viewModel::toggleSection) {
                                Text(
                                    text = "FICTION",
                                    style = MaterialTheme.typography.body1,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colors.primary
                                )
                            }
                        }
                        null -> {}
                    }
                }
            )
        },
        drawerShape = MaterialTheme.shapes.small,
        drawerContent = {
            DrawerItem(
                drawerIcon = Icons.Filled.LibraryBooks,
                drawerText = "Bookmarks",
                scope = scope,
                scaffoldState = scaffoldState,
                onClickAction = { navController.navigate(Screen.BookmarkList.route) }
            )
        },
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar(
                    viewModel = viewModel,
                    mode = mode
                )
                BookList(
                    mode = mode,
                    scrollState = scrollState,
                    state = state,
                    navController = navController,
                    snackbarController = snackbarController,
                    scaffoldState = scaffoldState,
                    viewModel = viewModel
                )
            }
        })
}

@Composable
fun SearchBar(
    viewModel: BookListViewModel,
    mode: Mode
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 5.dp, top = 10.dp, bottom = 2.dp)
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
        FilterSection(viewModel, mode)
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun BookList(
    mode: Mode,
    scrollState: LazyListState,
    state: BookListState,
    navController: NavController,
    snackbarController: SnackbarController,
    scaffoldState: ScaffoldState,
    viewModel: BookListViewModel
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = scrollState
    ) {
        items(state.bookList) { book ->
            BookItem(mode, navController, book)
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

@Composable
fun DrawerItem(
    drawerIcon: ImageVector,
    drawerText: String,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    onClickAction: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .height(45.dp)
            .clickable {
                onClickAction()
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            drawerIcon,
            modifier = Modifier.padding(end = 5.dp),
            contentDescription = null,
            tint = MaterialTheme.colors.primary
        )
        Text(
            text = drawerText,
            color = MaterialTheme.colors.primary,
            fontSize = 16.sp
        )
    }
}