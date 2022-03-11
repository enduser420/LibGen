package com.project.libgen.presentation.bookmark_list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.project.libgen.Screen
import com.project.libgen.presentation.book_list.components.BookItem
import com.project.libgen.presentation.components.ConfirmDialog
import com.project.libgen.presentation.components.util.SnackbarController
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun BookmarkListScreen(
    navController: NavController
) {
    ScreenContent(navController)
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ScreenContent(
    navController: NavController,
    viewModel: BookmarkListViewModel = hiltViewModel()
) {
    val state = viewModel.bookmarkList.value
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberLazyListState()
    val snackbarController = SnackbarController(viewModel.viewModelScope)
    LaunchedEffect(key1 = true, block = {
        viewModel.getBookmarks()
    })
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        ConfirmDialog(
            title = "Confirm Delete?",
            content = "Are you sure you want to delete all the Bookmarks?",
            onDismiss = { showDialog = false },
            onConfirm = {
                showDialog = false
                viewModel.onEvent(BookmarkListEvent.deleteAllBookmark)
            }
        )
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Bookmarks",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )
                },
                elevation = 5.dp,
                actions = {
//                    IconButton(onClick = { viewModel.getLocalBookmarks() }) {
//                        Icon(Icons.Filled.Recycling, contentDescription = null) // was used to test the init {..} puzzle I had, look line 42:
//                    }
                    IconButton(onClick = {
                        showDialog = true
                    }) {
                        Icon(Icons.Filled.Delete, contentDescription = null)
                    }
                }
            )
        },
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    state = scrollState
                ) {
                    items(state.bookmarkList) { bookmark ->
                        BookItem(navController, bookmark)
                    }
                }
                if (state.error.isNotBlank()) {
                    snackbarController.getScope().launch {
                        snackbarController.showSnackbar(
                            scaffoldState = scaffoldState,
                            message = "Something went wrong."
                        )
                    }
                }
                if (state.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                if (state.bookmarkList.isEmpty() and !state.isLoading) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "No Bookmarks.",
                        style = MaterialTheme.typography.h5,
                    )
                }

            }
        })
}
