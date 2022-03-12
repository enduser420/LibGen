package com.project.libgen.presentation.book_details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.project.libgen.R
import com.project.libgen.presentation.book_details.BookDetailsEvent
import com.project.libgen.presentation.book_details.fiction_book_details.FictionBookDetailsViewModel

@Composable
fun FictionBookDetailItem(
    viewModel: FictionBookDetailsViewModel
) {
    LaunchedEffect(key1 = viewModel.bookState, block = {
        viewModel.getBookDetails()
    })
    viewModel.bookState.value.book?.let { book ->
        val bookmarked by remember { viewModel.bookmarked }
        val painter = rememberImagePainter(
            data = "https://libgen.rs${book.coverurl}",
            builder = {
                placeholder(R.drawable.loading)
                error(R.drawable.error)
                fallback(R.drawable.error)
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        RowItem(header = "Title:", content = book.title ?: "N/A", maxLines = 2, contentIcon = {
            if (bookmarked == true) {
                IconButton(onClick = {
                    viewModel.onEvent(BookDetailsEvent.unstarBook)
                }) {
                    Icon(Icons.Filled.Star, tint = Color.Yellow, contentDescription = null)
                }
            } else {
                IconButton(onClick = {
                    viewModel.onEvent(BookDetailsEvent.starBook)
                }) {
                    Icon(Icons.Default.StarBorder, contentDescription = null)
                }
            }
        })
        RowItem(header = "Author(s):", content = book.author ?: "N/A")
        RowItem(
            header = "Description:",
            verticalAlignment = Alignment.Top,
            content = book.descr ?: "N/A",
            maxLines = 5,
            contentStyle = MaterialTheme.typography.subtitle1
        )
        RowItem(header = "Year:", content = book.year ?: "N/A")
        RowItem(header = "Volume:", content = book.volumeinfo ?: "N/A")
        RowItem(header = "Series:", content = book.series ?: "N/A")
        RowItem(header = "Edition:", content = book.edition ?: "N/A")
        RowItem(header = "Publisher:", content = book.publisher ?: "N/A")
        RowItem(header = "City:", content = book.city ?: "N/A")
        RowItem(header = "Pages:", content = book.pages ?: "N/A")
        RowItem(header = "Language:", content = book.language ?: "N/A")
        RowItem(header = "ISBN(s):", content = book.issn ?: "N/A")
        RowItem(header = "Torrent:", content = book.torrent ?: "N/A", setSpacer = false)
    }
}

@Composable
private fun RowItem(
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    header: String,
    content: String,
    maxLines: Int = 2,
    contentStyle: TextStyle = MaterialTheme.typography.body1,
    contentIcon: @Composable () -> Unit = {},
    setSpacer: Boolean = true
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = verticalAlignment,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = header,
                style = MaterialTheme.typography.h6,
            )
            Text(
                modifier = modifier,
                maxLines = maxLines,
                text = content,
                overflow = TextOverflow.Ellipsis,
                style = contentStyle
            )
            contentIcon()
        }
        if (setSpacer) Spacer(modifier = Modifier.height(8.dp))
    }
}