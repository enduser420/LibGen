package com.project.libgen.presentation.bookmark_details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.project.libgen.presentation.bookmark_details.components.BookmarkDetailItem
import com.project.libgen.presentation.components.util.SnackbarController
import kotlinx.coroutines.launch

@Composable
fun BookmarkDetailsScreen(
    navController: NavController
) {
    ScreenContent(navController)
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun ScreenContent(
    navController: NavController,
    viewModel: BookmarkDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.bookState.value
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberLazyListState()
    val snackbarController = SnackbarController(viewModel.viewModelScope)

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                snackbarController.getScope().launch {
                    viewModel.onEvent(BookmarkDetailsEvent.downloadBook)
                    snackbarController.showSnackbar(
                        scaffoldState = scaffoldState,
                        message = "Downloading...",
                        actionLabel = "OK"
                    )
                }
            }) {
                Icon(Icons.Filled.Download, contentDescription = null)
            }
        },
        content = {
            state.book?.let {
                LazyColumn(
                    state = scrollState
                ) {
                    item {
                        BookmarkDetailItem(it, viewModel)
                    }
                }
            }
            if (state.error.isNotEmpty()) {
                Text(
                    text = ";(",
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
                snackbarController.getScope().launch {
                    snackbarController.showSnackbar(
                        scaffoldState = scaffoldState,
                        message = "Network Error. Please check your Internet connection"
                    )
                }
            }
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    )
}