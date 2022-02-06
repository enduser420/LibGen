package com.project.libgen.presentation.book_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    val scaffoldState = rememberScaffoldState()
    val painter = rememberImagePainter(
        data = viewModel.bookState.value.coverurl,
        builder = {
            placeholder(R.drawable._6d96263885635_5acd0047cf3e6)
            error(R.drawable._6d96263885635_5acd0047cf3e6)
            fallback(R.drawable._6d96263885635_5acd0047cf3e6)
        }
    )
    println("https://library.lol/covers/${viewModel.bookState.value.coverurl}")
    println(viewModel.bookState.value.title)
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
//        Image(
//            painter = painterResource(id = R.drawable._6d96263885635_5acd0047cf3e6),
//            contentDescription = null,
//            modifier = Modifier.fillMaxWidth()
//        )
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Title: ${viewModel.bookState.value.title}",
                maxLines = 2,
                style = MaterialTheme.typography.h5
            )
        }
    }
}
//
//@Preview
//@Composable
//fun Details() {
//    ScreenContent()
//}