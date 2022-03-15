package com.project.libgen.presentation.book_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.libgen.Screen
import com.project.libgen.data.model.Book
import com.project.libgen.presentation.components.util.Mode
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun BookItem(
    mode: Mode,
    navController: NavController,
    book: Book
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {
            when (mode) {
                Mode.NONFICTION -> {
                    val encodedLink =
                        URLEncoder.encode(book.downloadlink, StandardCharsets.UTF_8.toString())
                    navController.navigate(
                        route = Screen.BookDetails.passIdandLink(
                            book.id,
                            encodedLink
                        )
                    )

                }
                Mode.FICTION -> {
                    val encodedLink =
                        URLEncoder.encode(book.downloadlink, StandardCharsets.UTF_8.toString())
                    val extension = book.extension!!.split("/").first().trim()
                    val filesize = book.extension!!.split("/").last().trim()
                    navController.navigate(
                        route = Screen.FictionBookDetails.passMd5andLinkandOthers(
                            book.md5.toString(),
                            encodedLink,
                            book.language ?: "NA",
                            book.series ?: "NA",
                            extension,
                            filesize
                        )
                    )
                }
            }
        }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                verticalAlignment = Alignment.Top
            ) {
                if (!book.id.contains(Regex("[a-zA-z]"))) {
                    Text(
                        text = book.id,
                        modifier = Modifier.padding(end = 5.dp),
                        style = MaterialTheme.typography.body2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = book.title ?: "N/A",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "by ${book.author ?: "N/A"}",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .weight(1f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Row {
                    if (book.pages?.isNotBlank() == true) {
                        Text(text = "Pages: ${book.pages}", modifier = Modifier.padding(end = 5.dp))
                    }
                    Text(text = book.extension ?: "N/A")
                }
            }
        }
    }
}