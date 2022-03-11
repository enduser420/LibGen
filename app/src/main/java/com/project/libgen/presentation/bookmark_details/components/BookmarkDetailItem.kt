package com.project.libgen.presentation.bookmark_details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import coil.compose.rememberImagePainter
import com.project.libgen.R
import com.project.libgen.data.model.Book
import com.project.libgen.presentation.book_details.BookDetailsViewModel
import com.project.libgen.presentation.bookmark_details.BookmarkDetailsEvent
import com.project.libgen.presentation.bookmark_details.BookmarkDetailsViewModel
import com.project.libgen.presentation.components.util.SnackbarController

@Composable
fun BookmarkDetailItem(
    book: Book,
    viewModel: BookmarkDetailsViewModel
) {
    val painter = rememberImagePainter(
        data = "http://library.lol/covers/${book.coverurl}",
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
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Title:",
            style = MaterialTheme.typography.h6
        )
        Text(
            text = book.title?: "N/A",
            style = MaterialTheme.typography.body1
        )
        IconButton(onClick = {
//            if (book.bookmarked) {
//                viewModel.onEvent(BookmarkDetailsEvent.starBook())
//            }
//            else {
//                viewModel.starBook()
//            }
        }) {
            if (book.bookmarked)
                Icon(Icons.Filled.Star, tint = Color.Yellow, contentDescription = null)
            else
                Icon(Icons.Default.StarBorder, contentDescription = null)
        }
    }
    Spacer(modifier = Modifier.height(5.dp))

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Author(s):",
            style = MaterialTheme.typography.h6
        )
        Text(
            text = book.author?: "N/A",
            style = MaterialTheme.typography.body1
        )
    }
    Spacer(modifier = Modifier.height(5.dp))

    Column {
        Text(
            text = "Description:",
            style = MaterialTheme.typography.h6
        )
        Text(
            text = book.descr?: "N/A",
            style = MaterialTheme.typography.subtitle2
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Year:",
            style = MaterialTheme.typography.h6
        )
        Text(
            text = book.year?: "N/A",
            style = MaterialTheme.typography.body1
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Volume:",
            style = MaterialTheme.typography.h6
        )
        Text(
            text = book.volumeinfo?: "N/A",
            style = MaterialTheme.typography.body1
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Series:",
            style = MaterialTheme.typography.h6
        )
        Text(
            text = book.series?: "N/A",
            style = MaterialTheme.typography.body1
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Edition:",
            style = MaterialTheme.typography.h6
        )
        Text(
            text = book.edition?: "N/A",
            style = MaterialTheme.typography.body1
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Publisher:",
            style = MaterialTheme.typography.h6
        )
        Text(
            text = book.publisher?: "N/A",
            style = MaterialTheme.typography.body1
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "City:",
            style = MaterialTheme.typography.h6
        )
        Text(
            text = book.city?: "N/A",
            style = MaterialTheme.typography.body1
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Pages:",
            style = MaterialTheme.typography.h6
        )
        Text(
            text = book.pages?: "N/A",
            style = MaterialTheme.typography.body1
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Language:",
            style = MaterialTheme.typography.h6
        )
        Text(
            text = book.language?: "N/A",
            style = MaterialTheme.typography.body1
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "ISBN(s):",
            style = MaterialTheme.typography.h6
        )
        Text(
            text = book.issn?: "N/A",
            style = MaterialTheme.typography.body1
        )
    }
}