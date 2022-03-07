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
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun BookItem(
    navController: NavController,
    book: Book
) {
    fun onBookClicked() {
        val encodedLink = URLEncoder.encode(book.downloadlink, StandardCharsets.UTF_8.toString())
            navController.navigate(route = Screen.BookDetails.passIdandLink(book.id, encodedLink))
    }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = { onBookClicked() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp)
            ) {
                Text(
                    text = book.id,
                    modifier = Modifier.padding(end = 5.dp),
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.title?.ifBlank { "N/A" }.toString(),
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
                    text = "by ${book.author}",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .weight(1f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Row {
                    Text(text = "Pages: ${book.pages}", modifier = Modifier.padding(end = 5.dp))
                    Text(text = book.extension?.ifBlank { "N/A" }.toString())
                }
            }
        }
    }
}