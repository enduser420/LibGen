package com.project.libgen.presentation.book_details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.project.libgen.R
import com.project.libgen.data.model.Book

@Composable
fun BookDetailItem(
    book: Book
) {
    /* https://library.lol/covers/0/690ff9f017c663a7068803ea1705d091.jpg */
    val painter = rememberImagePainter(
        data = "https://library.lol/covers/${book.coverurl}",
        builder = {
            placeholder(R.drawable._6d96263885635_5acd0047cf3e6)
            error(R.drawable._6d96263885635_5acd0047cf3e6)
            fallback(R.drawable._6d96263885635_5acd0047cf3e6)
        }
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = null
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = "Title: ${book.title}",
        style = MaterialTheme.typography.h6
    )
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = "Author(s): ${book.author}",
        style = MaterialTheme.typography.h6
    )
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = "Description: ${book.descr}",
        style = MaterialTheme.typography.subtitle2
    )
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = "Year: ${book.year}",
        style = MaterialTheme.typography.h6
    )
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = "Volume: ${book.volumeinfo}",
        style = MaterialTheme.typography.h6
    )
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = "Series: ${book.series}",
        style = MaterialTheme.typography.h6
    )
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = "Edition: ${book.edition}",
        style = MaterialTheme.typography.h6
    )
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = "Publisher: ${book.publisher}",
        style = MaterialTheme.typography.h6
    )
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = "City: ${book.city}",
        style = MaterialTheme.typography.h6
    )
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = "Pages: ${book.pages}",
        style = MaterialTheme.typography.h6
    )
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = "Language: ${book.language}",
        style = MaterialTheme.typography.h6
    )
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = "ISBN(s): ${book.issn}",
        style = MaterialTheme.typography.h6
    )
}