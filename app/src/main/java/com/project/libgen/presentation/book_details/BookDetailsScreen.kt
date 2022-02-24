package com.project.libgen.presentation.book_details

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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.project.libgen.presentation.book_details.components.BookDetailItem
import com.project.libgen.presentation.components.util.SnackbarController
import kotlinx.coroutines.launch

@Composable
fun BookDetailsScreen(
    navController: NavController
) {
    ScreenContent(navController)
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun ScreenContent(
    navController: NavController,
    viewModel: BookDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.bookState.value
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberLazyListState()
    val snackbarController = SnackbarController(viewModel.viewModelScope)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Details",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
//                             navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                snackbarController.getScope().launch {
                    viewModel.onEvent(BookDetailsEvent.downloadBook)
                    snackbarController.showSnackbar(
                        scaffoldState = scaffoldState,
                        message = "Downloading...",
                        actionLabel = "OK"
                    )
                }.invokeOnCompletion {
                    viewModel.onEvent(BookDetailsEvent.downloadBook)
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
                        BookDetailItem(viewModel)
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

fun customShape() = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(Rect(0f, 0f, 100f /* width */, 131f /* height */))
    }
}