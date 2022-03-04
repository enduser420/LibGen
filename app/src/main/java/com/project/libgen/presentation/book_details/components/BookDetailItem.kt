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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.project.libgen.R
import com.project.libgen.presentation.book_details.BookDetailsEvent
import com.project.libgen.presentation.book_details.BookDetailsViewModel
import com.project.libgen.presentation.components.util.UserState

@Composable
fun BookDetailItem(
    viewModel: BookDetailsViewModel
) {
    val currentUser by viewModel.currentUser.observeAsState(UserState())
    viewModel.bookState.value.book?.let { book ->
        val bookmarked by remember {
            viewModel.bookmarked
        }
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
                text = book.title?.ifBlank { "N/A" }.toString(),
                style = MaterialTheme.typography.body1
            )
            currentUser.user?.let {
                if (!it.isAnonymous) {
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
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Author(s):",
                style = MaterialTheme.typography.h6
            )
            Text(
                text = book.author?.ifBlank { "N/A" }.toString(),
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
                text = book.descr?.ifBlank { "N/A" }.toString(),
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
                text = book.year?.ifBlank { "N/A" }.toString(),
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
                text = book.volumeinfo?.ifBlank { "N/A" }.toString(),
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
                text = book.series?.ifBlank { "N/A" }.toString(),
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
                text = book.edition?.ifBlank { "N/A" }.toString(),
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
                text = book.publisher?.ifBlank { "N/A" }.toString(),
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
                text = book.city?.ifBlank { "N/A" }.toString(),
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
                text = book.pages?.ifBlank { "N/A" }.toString(),
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
                text = book.language?.ifBlank { "N/A" }.toString(),
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
                text = book.issn?.ifBlank { "N/A" }.toString(),
                style = MaterialTheme.typography.body1
            )

        }
    }
}