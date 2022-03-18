package com.project.libgen.presentation.book_details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.project.libgen.R
import com.project.libgen.presentation.book_details.BookDetailsEvent
import com.project.libgen.presentation.book_details.BookDetailsViewModel

@Composable
fun BookDetailItem(
    viewModel: BookDetailsViewModel
) {
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
        Column {
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
            RowItem(
                header = "Title:",
                content = book.title,
                contentIcon = {
                    if (bookmarked == true) {
                        IconButton(onClick = {
                            viewModel.onEvent(BookDetailsEvent.unstarBook)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                tint = Color.Yellow,
                                contentDescription = null
                            )
                        }
                    } else {
                        IconButton(onClick = {
                            viewModel.onEvent(BookDetailsEvent.starBook)
                        }) {
                            Icon(
                                imageVector = Icons.Default.StarBorder,
                                contentDescription = null
                            )
                        }
                    }
                })
            RowItem(header = "Author(s):", content = book.author ?: "N/A")
            RowItem(
                header = "Description:",
                content = book.descr ?: "N/A",
                contentStyle = MaterialTheme.typography.subtitle1
            )
            RowItem(header = "Year:", content = book.year)
            RowItem(header = "Volume:", content = book.volumeinfo)
            RowItem(header = "Series:", content = book.series)
            RowItem(header = "Edition:", content = book.edition)
            RowItem(header = "Publisher:", content = book.publisher)
            RowItem(header = "City:", content = book.city)
            RowItem(header = "Pages:", content = book.pages)
            RowItem(header = "Language:", content = book.language)
            RowItem(header = "ISBN(s):", content = book.issn)
            RowItem(
                header = "Torrent:",
                content = book.torrent,
                setSpacer = false,
                onClick = { viewModel.onEvent(BookDetailsEvent.getTorrent) })
        }
    }
}

@Composable
private fun RowItem(
    header: String,
    content: String?,
    contentStyle: TextStyle = MaterialTheme.typography.body1,
    contentIcon: @Composable () -> Unit = {},
    setSpacer: Boolean = true,
    onClick: () -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = header,
                style = MaterialTheme.typography.h5,
            )
            contentIcon()
        }
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clickable {
                    onClick()
                },
            text = content.toString().ifBlank { "N/A" },
            overflow = TextOverflow.Ellipsis,
            style = contentStyle
        )
        if (setSpacer) Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview
@Composable
fun Test() {
    RowItem(header = "TEXT", content = "TEXTTTTTTT", contentIcon = {
        Icon(Icons.Default.Star, contentDescription = null)
    })
}