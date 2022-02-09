package com.project.libgen.presentation.book_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.project.libgen.presentation.book_details.components.BookDetailItem
import com.project.libgen.presentation.components.util.SnackbarController
import kotlinx.coroutines.launch

@Composable
fun BookDetailsScreen() {
    ScreenContent()
}

@Composable
private fun ScreenContent(
    viewModel: BookDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.bookState.value
    val scaffoldState = rememberScaffoldState()
    val snackbarController = SnackbarController(viewModel.viewModelScope)

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                snackbarController.getScope().launch {
                    snackbarController.showSnackbar(
                        scaffoldState = scaffoldState,
                        message = "Downloading...",
                        actionLabel = "OK"
                    )
                }
            }) {
                Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
            }
        },
        content = {
            state.book?.let {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        BookDetailItem(it)
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
        })
}