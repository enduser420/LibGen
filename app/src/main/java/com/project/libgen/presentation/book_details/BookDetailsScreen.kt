package com.project.libgen.presentation.book_details

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
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
    val downloadState = viewModel.downloadState.value
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberLazyListState()
    val snackbarController = SnackbarController(viewModel.viewModelScope)
    var menuShow by remember { mutableStateOf(false) }
    val context = LocalContext.current
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
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { menuShow = true }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = null)
                    }
                    DropdownMenu(expanded = menuShow, onDismissRequest = { menuShow = !menuShow }) {
                        val downloadlink = viewModel.downloadlink.value
                        DropdownMenuItem(onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND)
                            shareIntent.type = "text/plain"
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share Link")
                            shareIntent.putExtra(
                                Intent.EXTRA_TEXT,
                                "Check out\n$downloadlink"
                            )
                            val chooser = Intent.createChooser(shareIntent, "Share")
                            try {
                                startActivity(context, chooser, null)
                            } catch (e: ActivityNotFoundException) {
                                e.printStackTrace()
                            }
                            menuShow = false
                        }) {
                            Text("Share")
                        }
                        DropdownMenuItem(onClick = {
                            val link = Uri.parse(downloadlink)
                            val browserIntent = Intent(Intent.ACTION_VIEW, link)
                            try {
                                startActivity(context, browserIntent, null)
                            } catch (e: ActivityNotFoundException) {
                                e.printStackTrace()
                            }
                            menuShow = false
                        }) {
                            Text("Open in Browser")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(BookDetailsEvent.downloadBook)
            }) {
                Icon(Icons.Filled.Download, contentDescription = null)
            }
        },
        content = {
            LazyColumn(
                state = scrollState
            ) {
                item {
                    BookDetailItem(viewModel)
                }
            }
            if (state.error.isNotBlank()) {
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
                        message = state.error
                    )
                }
            }
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            if (downloadState.isLoading) {
                snackbarController.getScope().launch {
                    snackbarController.showSnackbar(
                        scaffoldState = scaffoldState,
                        message = "Downloading ..."
                    )
                }
            }
            if (downloadState.error.isNotBlank()) {
                snackbarController.getScope().launch {
                    snackbarController.showSnackbar(
                        scaffoldState = scaffoldState,
                        message = downloadState.error
                    )
                }
            }
        })
}
