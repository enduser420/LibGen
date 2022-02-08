package com.project.libgen.presentation.book_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.project.libgen.R

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
    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        state.book?.let { book ->
            val painter = rememberImagePainter(
                data = "https://library.lol/covers/${book.coverurl}",
                builder = {
                    placeholder(R.drawable._6d96263885635_5acd0047cf3e6)
                    error(R.drawable._6d96263885635_5acd0047cf3e6)
                    fallback(R.drawable._6d96263885635_5acd0047cf3e6)
                }
            )
            //https://library.lol/covers/0/690ff9f017c663a7068803ea1705d091.jpg
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                    ) {
                        Image(
                            painter = painter,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Title: ${book.title}",
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = "Author(s): ${book.author}",
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = "Description: ${book.descr}",
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = "Year: ${book.year}",
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = "Volume: ${book.volumeinfo}",
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = "Series: ${book.series}",
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = "Edition: ${book.edition}",
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = "Publisher: ${book.publisher}",
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = "City: ${book.city}",
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = "Pages: ${book.pages}",
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = "Language: ${book.language}",
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = "ISBN(s): ${book.issn}",
                        style = MaterialTheme.typography.h5
                    )
                }
            }
        }
        if (state.error.isNotEmpty()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}